package es.unican.ps.uc_hoteles.common.business;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.uc_hoteles.common.domain.DatosCliente;
import es.unican.ps.uc_hoteles.common.domain.DatosPago;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import jakarta.ejb.Local;

@Local 
public interface IGestionReservasUsuario {
    /**
     * Busca un hotel por su nombre
     * @param nombre nombre del hotel
     * @return el hotel en caso de que exista, null en caso contrario
     */
    public Hotel buscarHotelPorNombre(String nombre);

    /**
     * Busca hoteles en funcion de su localidad
     * @param localidad localidad de los hoteles a buscar
     * @return la lista de hoteles que estan en la localidad introducida
     */
    public List<Hotel> buscarHotelPorLocalidad(String localidad);

    /**
     * Consulta una reserva concreta
     * @param idReserva id de la reserva
     * @param dni dni del cliente que ha reservado
     * @return la reserva asociada a los datos de entrada, null en caso contrario
     */
    public Reserva consultarReserva(int idReserva, String dni);
    
    /**
     * Cancela una reserva 
     * @param idReserva id de la reserva
     * @param dni dni del cliente que ha reservado
     * @return true si se ha cancelado la reserva, false en caso contrario
     */
    public boolean cancelarReserva(int idReserva, String dni);
    
    /**
     * Inicia una reserva en curso
    * @param h hotel del cual se va a realizar la reserva
    * @param fechaEntrada fecha de entrada al hotel
    * @param fechaSalida fecha de salida del hotel
    * @return la lista con las habitaciones disponibles en el hotel
    */
    public List<Habitacion> iniciarReserva(Hotel h, LocalDate fechaEntrada, LocalDate fechaSalida);

    /**
     * Anhade una o varias habitaciones a una reserva en curso
     * @param hab habitacion a anhadir
     * @param cantidad numero de habitaciones
     * @return el precio estimado de la adicion de las habitaciones
     */
    public double anhadirHabitacion(Habitacion hab, int cantidad);
    
    /**
     * Confirma una reserva en curso
     * @param cliente datos del cliente
     * @param pago datos de pago
     * @return el id de la reserva
     */
    public int confirmarReserva(DatosCliente cliente, DatosPago pago);
}
