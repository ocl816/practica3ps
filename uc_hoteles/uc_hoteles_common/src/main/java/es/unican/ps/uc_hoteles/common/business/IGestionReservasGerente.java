package es.unican.ps.uc_hoteles.common.business;

import java.util.List;

import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import jakarta.ejb.Local;

@Local 
public interface IGestionReservasGerente {
    /**
     * Obtiene las reservas de un hotel
     * @param h hotel del cual se van a obtener las reservas
     * @return una lista con todas las reservas del hotel
     */
    public List<Reserva> consultarReservasHotel(Hotel h);
    
    /**
     * Obtiene un hotel en funcion de su nombre
     * @param nombre nombre del hotel
     * @return el hotel en caso de existir, null en caso contrario
     */
    public Hotel consultarHotel(String nombre);
}
