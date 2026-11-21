package es.unican.ps.uc_hoteles.dao;

import java.util.List;

import es.unican.ps.uc_hoteles.common.dao.IHotelDAO;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Stateless
public class HotelDAO implements IHotelDAO {

    @PersistenceContext(unitName = "uchotelesPU")
    private EntityManager em;

    /**
     * @see IHotelDAO#anhadeHotel(Hotel)
     */
    public boolean anhadeHotel(Hotel h) {
        if (buscaHotel(h.getNombre()) != null) {
            return false;
        }

        try {
            em.persist(h);
            return true;
        } catch (EntityExistsException e) {
            return false;
        }
    }

    /**

     * @see IHotelDAO#modificaHotel(Hotel)
     */
    public Hotel modificaHotel(Hotel h) {
        Hotel hotelExistente = em.find(Hotel.class, h.getId());
        
        if (hotelExistente == null) {
            return null;
        }

        for (Habitacion hab : hotelExistente.getHabitaciones()) {
            boolean existeEnModificacion = false;
            
            for (Habitacion habNueva : h.getHabitaciones()) {
                if (habNueva.getId() == hab.getId()) {
                    existeEnModificacion = true;
                }
            }
            
            if (!existeEnModificacion) {
                em.remove(hab);
            }
        }
        return em.merge(h);
    }

    /**
     * @see IHotelDAO#eliminaHotel(Hotel)
     */
    public Hotel eliminaHotel(Hotel h) {
        Hotel hotelExistente = em.find(Hotel.class, h.getId());
        
        if (hotelExistente == null) {
            return null;
        }

        em.remove(hotelExistente);
        return hotelExistente;
    }

    /**
     * @see IHotelDAO#buscaHotel(String)
     */
    public Hotel buscaHotel(String nombre) {
        Query q = em.createQuery("SELECT h FROM Hoteles h WHERE h.nombre = :nombre");
        q.setParameter("nombre", nombre);
        
        try {
            return (Hotel) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * @see IHotelDAO#buscaHoteles()
     */
    @SuppressWarnings("unchecked")
    public List<Hotel> buscaHoteles() {
        Query q = em.createQuery("SELECT h FROM Hoteles h");
        return q.getResultList();
    }

    /**
     * @see IHotelDAO#buscaHotelPorLocalidad(String)
     */
    @SuppressWarnings("unchecked")
    public List<Hotel> buscaHotelPorLocalidad(String localidad) {
        Query q = em.createQuery("SELECT h FROM Hotel h WHERE h.localidad = :localidad");
        q.setParameter("localidad", localidad);
        return q.getResultList();
    }
}