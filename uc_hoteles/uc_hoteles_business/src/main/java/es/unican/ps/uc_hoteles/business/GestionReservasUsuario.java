package es.unican.ps.uc_hoteles.business;

import java.time.LocalDate;
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

        if (r != null && r.getDatosCliente().getDni().equals(dni)) {
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
        return h.getHabitaciones();
    }

    
    /**
     * @see IGestionReservasUsuario#anhadirHabitacion()
     */
    @Override 
    public double anhadirHabitacion(Habitacion hab, int cantidad) {
        if (cantidad > 0) {
            LineaReserva linea = new LineaReserva(cantidad, hab);
            this.reservaEnCurso.getLineas().add(linea);
        }

        return this.reservaEnCurso.getImporte();
    }

    /**
     * @see IGestionReservasUsuario#confirmarReserva()
     */
    @Override
    public int confirmarReserva(DatosCliente cliente, DatosPago pago) {
        reservaEnCurso.setDatosCliente(cliente);
        reservaEnCurso.setDatosPago(pago);
        
        if (reservasDAO.anhadeReserva(reservaEnCurso)) {
            return reservaEnCurso.getId();
        }

        return -1;
    }

}
