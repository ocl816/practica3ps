package es.unican.ps.uc_hoteles.business;

import java.util.List;

import es.unican.ps.uc_hoteles.common.business.IGestionHoteles;
import es.unican.ps.uc_hoteles.common.dao.IHotelDAO;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.TipoHabitacion;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless 
public class GestionHoteles implements IGestionHoteles {

    @EJB 
    private IHotelDAO hotelDAO;

    public GestionHoteles(IHotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    /**
     * @see IGestionHoteles#anhadirTipoHabitacion()
     */
    @Override
    public boolean anhadirTipoHabitacion(Hotel h, Habitacion hab) {
        h.getHabitaciones().add(hab);
        Hotel hotelMod = hotelDAO.modificaHotel(h);
        return hotelMod != null;
    }

    /**
     * @see IGestionHoteles#modificarHabitacion()
     */
    @Override
    public boolean modificarHabitacion(Hotel h, Habitacion hab) {
        List<Habitacion> habitaciones = h.getHabitaciones();
        
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo() == hab.getTipo()) {
                habitaciones.remove(habitacion);
                habitaciones.add(hab);
                break;
            }    
        }

        h.setHabitaciones(habitaciones);
        Hotel hotelMod = hotelDAO.modificaHotel(h);
        return hotelMod != null;
    }

    @Override
    public boolean eliminarTipoHabitacion(Hotel h, TipoHabitacion tipo) {
        List<Habitacion> habitaciones = h.getHabitaciones();
        
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo() == tipo) {
                habitaciones.remove(habitacion);
            }    
        }

        h.setHabitaciones(habitaciones);
        Hotel hotelMod = hotelDAO.modificaHotel(h);
        return hotelMod != null;
    }

}
