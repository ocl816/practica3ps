package es.unican.ps.uc_hoteles.dao;

import java.util.List;

import es.unican.ps.uc_hoteles.common.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.common.domain.DatosCliente;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class ReservasDAO implements IReservasDAO {

    @PersistenceContext(unitName = "uchotelesPU")
    private EntityManager em;

    /**
     * @see IReservasDAO#anhadeReserva(Reserva)
     */
    @SuppressWarnings("unchecked")
    public boolean anhadeReserva(Reserva r) {
        try {
            //Busca si los datos del cliente existen en la base de datos
            Query q = em.createQuery("SELECT c FROM DatosCliente c WHERE c.dni = :dni");
            q.setParameter("dni", r.getDatosCliente().getDni());
            List<Object> clientes = q.getResultList();

            //Si existe el cliente, lo introduce en la reserva para no volver a persistirlo
            if (!clientes.isEmpty()) {
                r.setDatosCliente((DatosCliente) clientes.get(0));
            }

            em.persist(r);
            em.flush();
            return true;
        } catch (EntityExistsException e) {
            return false;
        }
    }

    /**
     * @see IReservasDAO#modificaReserva(Reserva)
     */
    public Reserva modificaReserva(Reserva r) {
        if (em.find(Reserva.class, r.getId()) == null) {
            return null;
        }
        
        return em.merge(r);
    }

    /**
     * @see IReservasDAO#eliminaReserva(Reserva)
     */
    public Reserva eliminaReserva(Reserva r) {
        Reserva existente = em.find(Reserva.class, r.getId());
        
        if (existente == null) {
            return null;
        }
        
        em.remove(existente);
        return existente;
    }

    /**
     * @see IReservasDAO#buscaReserva(int)
     */
    public Reserva buscaReserva(int idReserva) {
        return em.find(Reserva.class, idReserva);
    }

    /**
     * @see IReservasDAO#buscaReservas()
     */
    @SuppressWarnings("unchecked")
    public List<Reserva> buscaReservas() {
        Query q = em.createQuery("SELECT r FROM Reserva r");
        return q.getResultList();
    }

    /**
     * @see IReservasDAO#buscaReservasPorHotel(String)
     */
    @SuppressWarnings("unchecked")
    public List<Reserva> buscaReservasPorHotel(String nombreHotel) {
        Query q = em.createQuery("SELECT r FROM Reserva r WHERE r.hotel.nombre = :nombre ORDER BY r.fechaEntrada");
        q.setParameter("nombre", nombreHotel);
        return q.getResultList();
    }
}