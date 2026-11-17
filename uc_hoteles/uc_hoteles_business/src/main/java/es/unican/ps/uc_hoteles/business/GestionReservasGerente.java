package es.unican.ps.uc_hoteles.business;

import java.util.List;

import es.unican.ps.uc_hoteles.common.business.IGestionReservasGerente;
import es.unican.ps.uc_hoteles.common.dao.IHotelDAO;
import es.unican.ps.uc_hoteles.common.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless 
public class GestionReservasGerente implements IGestionReservasGerente {

    @EJB 
    private IReservasDAO reservasDAO;

    @EJB 
    private IHotelDAO hotelDAO;

    /**    
     * @see IGestionReservasGerente#consultarReservasHotel()
     */
    @Override
    public List<Reserva> consultarReservasHotel(Hotel h) {
        List<Reserva> reservas = reservasDAO.buscaReservasPorHotel(h.getNombre());
        
        if (reservas != null) {
            reservas.sort((r1, r2) -> r1.getFechaEntrada().compareTo(r2.getFechaEntrada()));
        }
        
        return reservas;
    }   

    /**    
     * @see IGestionReservasGerente#consultarHotel()
     */
    @Override
    public Hotel consultarHotel(String nombre) {
        return hotelDAO.buscaHotel(nombre);
    }

}
