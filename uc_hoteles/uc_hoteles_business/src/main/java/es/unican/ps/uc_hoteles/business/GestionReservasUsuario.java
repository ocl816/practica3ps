package es.unican.ps.uc_hoteles.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.uc_hoteles.common.business.IGestionReservasUsuario;
import es.unican.ps.uc_hoteles.common.dao.IHotelDAO;
import es.unican.ps.uc_hoteles.common.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.common.domain.DatosCliente;
import es.unican.ps.uc_hoteles.common.domain.DatosPago;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.LineaReserva;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;

@Stateful 
public class GestionReservasUsuario implements IGestionReservasUsuario {

    @EJB 
    private IHotelDAO hotelDao;
    
    @EJB
    private IReservasDAO reservasDAO;

    private Reserva reservaEnCurso;
    private List<Habitacion> disponibilidadHotel;
    private LocalDateTime fechaBloqueo;

    /**
     * @see IGestionReservasUsuario#buscarHotelPorNombre()
     */
    @Override
    public Hotel buscarHotelPorNombre(String nombre) {
        return hotelDao.buscaHotel(nombre);
    }

    /**
     * @see IGestionReservasUsuario#buscarHotelPorLocalidad()
     */
    @Override
    public List<Hotel> buscarHotelPorLocalidad(String localidad) {
        return hotelDao.buscaHotelPorLocalidad(localidad);
    }

    /**
     * @see IGestionReservasUsuario#consultarReserva()
     */
    @Override
    public Reserva consultarReserva(int idReserva, String dni) {
        Reserva r = reservasDAO.buscaReserva(idReserva);

        if (r != null  && r.getDatosCliente() != null && r.getDatosCliente().getDni().equals(dni)) {
            return r;
        }

        return null;
    }

    /**
     * @see IGestionReservasUsuario#cancelarReserva()
     */
    @Override
    public boolean cancelarReserva(int idReserva, String dni) {
        Reserva r = consultarReserva(idReserva, dni);
        if (r != null) {
            // Comprueba que no queden menos de dos dias para cancelar la reserva
            LocalDate fechaLimite = r.getFechaEntrada().minusDays(2);
            if (LocalDate.now().isAfter(fechaLimite) || LocalDate.now().isEqual(fechaLimite)) {
                return false; 
            }
            return reservasDAO.eliminaReserva(r) != null;
        }

        return false;
    }


    /**
     * @see IGestionReservasUsuario#iniciarReserva()
     */
    @Override
    public List<Habitacion> iniciarReserva(Hotel h, LocalDate fechaEntrada, LocalDate fechaSalida) {
        reservaEnCurso = new Reserva();
        reservaEnCurso.setHotel(h);
        reservaEnCurso.setFechaEntrada(fechaEntrada);
        reservaEnCurso.setFechaSalida(fechaSalida);
        reservaEnCurso.setLineas(new ArrayList<>());
        
        List<Reserva> reservasHotel = reservasDAO.buscaReservasPorHotel(h.getNombre());
        disponibilidadHotel = new ArrayList<>();

        //Calcula la disponibilidad actual de cada habitacion del hotel  
        for (Habitacion hab : h.getHabitaciones()) {
            int ocupadas = 0;

            if (reservasHotel != null) {
                for (Reserva r : reservasHotel) {
                    // Comprueba que no exista un solapamiento de fechas
                    if (r.getFechaEntrada().isBefore(fechaSalida) && r.getFechaSalida().isAfter(fechaEntrada)) {
                        for (LineaReserva linea : r.getLineas()) {
                            if (linea.getHabitacion().getTipo() == hab.getTipo()) {
                                ocupadas += linea.getNumHabitaciones();
                            }
                        }
                    }
                }
            }

            int libres = hab.getDisponibles() - ocupadas;
            if (libres > 0) {
                disponibilidadHotel.add(new Habitacion(hab.getPrecioPorNoche(), libres, hab.getTipo()));
            }
        }

        return disponibilidadHotel;
    }

    
    /**
     * @see IGestionReservasUsuario#anhadirHabitacion()
     */
    @Override 
    public double anhadirHabitacion(Habitacion hab, int cantidad) {
        if (reservaEnCurso == null || disponibilidadHotel == null || cantidad <= 0) {
            return -1;
        }

        // Comprueba que existen habitaciones suficientes para ese tipo 
        for (Habitacion disp : disponibilidadHotel) {
            if (disp.getTipo() == hab.getTipo()) {
                if (cantidad > disp.getDisponibles()) {
                    return -1; // No hay suficientes habitaciones libres de este tipo
                }
                break;
            }
        }

        LineaReserva linea = new LineaReserva(cantidad, hab);
        this.reservaEnCurso.getLineas().add(linea);
        
        // Inicia el bloqueo temporal
        this.fechaBloqueo = LocalDateTime.now();

        return this.reservaEnCurso.getImporte();
    }

    /**
     * @see IGestionReservasUsuario#confirmarReserva()
     */
    @Override
    public int confirmarReserva(DatosCliente cliente, DatosPago pago) {
        if (reservaEnCurso == null || fechaBloqueo == null) {
            return -1;
        }

        // Comprueba si se ha acabado el bloquedo de 10 minutos de la habitacion
        if (LocalDateTime.now().isAfter(fechaBloqueo.plusMinutes(10))) {
            reservaEnCurso = null;
            return -2; 
        }

        reservaEnCurso.setDatosCliente(cliente);
        reservaEnCurso.setDatosPago(pago);
        
        if (reservasDAO.anhadeReserva(reservaEnCurso)) {
            int id = reservaEnCurso.getId();
            reservaEnCurso = null;
            fechaBloqueo = null;
            return id;
        }

        return -1;
    }

}
