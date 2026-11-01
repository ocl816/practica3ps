package es.unican.ps.uc_hoteles.common.business;

import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.TipoHabitacion;
import jakarta.ejb.Local;

@Local 
public interface IGestionHoteles {
    /**
     * Anhade un nuevo tipo de habitacion a un hotel
     * @param h hotel
     * @param hab habitacion a anhadir
     * @return true si se ha anhadido correctamente, false en caso contrario
     */
    public boolean anhadirTipoHabitacion(Hotel h, Habitacion hab);
    
    /**
     * Modifica una habitacion de un hotel
     * @param h hotel
     * @param hab habitacion modificada
     * @return true si se ha modificado correctamente, false en caso contrario
     */
    public boolean modificarHabitacion(Hotel h, Habitacion hab);

    /**
     * Elimina habitaciones de un tipo de habitacion concreto dentro de un hotel
     * @param h hotel
     * @param tipo tipo de habitacion a eliminar
     * @return true si se ha eliminado correctamente, false en caso contrario
     */
    public boolean eliminarTipoHabitacion(Hotel h, TipoHabitacion tipo);
}
