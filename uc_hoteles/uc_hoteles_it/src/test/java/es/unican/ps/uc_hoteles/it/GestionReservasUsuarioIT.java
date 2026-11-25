package es.unican.ps.uc_hoteles.it;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import es.unican.ps.uc_hoteles.common.business.IGestionReservasUsuario;
import es.unican.ps.uc_hoteles.common.dao.IHotelDAO;
import es.unican.ps.uc_hoteles.common.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.common.domain.Categoria;
import es.unican.ps.uc_hoteles.common.domain.DatosCliente;
import es.unican.ps.uc_hoteles.common.domain.DatosPago;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.LineaReserva;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import es.unican.ps.uc_hoteles.common.domain.TipoHabitacion;
import es.unican.ps.uc_hoteles.common.domain.TipoTarjeta;
import jakarta.ejb.embeddable.EJBContainer;
 
public class GestionReservasUsuarioIT {
 
    private static EJBContainer ec;
    private static IGestionReservasUsuario sut;
    private static IHotelDAO hotelDAO;
    private static IReservasDAO reservasDAO;
 
    private Hotel hotel;
    private Habitacion habDoble;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
 
    @BeforeAll
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root",
                System.getenv("GLASSFISH_HOME"));
 
        ec = EJBContainer.createEJBContainer(properties);
 
        sut = (IGestionReservasUsuario) ec.getContext().lookup(
                "java:global/uc_hoteles_business-1.0-SNAPSHOT/GestionReservasUsuario!"
                + "es.unican.ps.uc_hoteles.common.business.IGestionReservasUsuario");
        hotelDAO = (IHotelDAO) ec.getContext().lookup(
                "java:global/uc_hoteles_dao-1.0-SNAPSHOT/HotelDAO!"
                + "es.unican.ps.uc_hoteles.common.dao.IHotelDAO");
        reservasDAO = (IReservasDAO) ec.getContext().lookup(
                "java:global/uc_hoteles_dao-1.0-SNAPSHOT/ReservasDAO!"
                + "es.unican.ps.uc_hoteles.common.dao.IReservasDAO");
    }
 
    @AfterAll
    public static void closeContainer() throws Exception {
        if (ec != null) {
            ec.close();
        }
    }
 
    @BeforeEach
    public void setUp() {
        String nombre = "Hotel";
 
        Hotel nuevo = new Hotel();
        nuevo.setNombre(nombre);
        nuevo.setDirecicon("Calle");
        nuevo.setLocalidad("Santander");
        nuevo.setCategoria(Categoria.FOUR_STARS);
        nuevo.getHabitaciones().add(new Habitacion(100.0, 5, TipoHabitacion.DOBLE));
        hotelDAO.anhadeHotel(nuevo);
 
        hotel = hotelDAO.buscaHotel(nombre);
        habDoble = hotel.getHabitaciones().get(0);
 
        fechaEntrada = LocalDate.of(2025, 5, 10);
        fechaSalida = LocalDate.of(2025, 5, 15);
    }
 
    @AfterEach
    public void tearDown() {
        List<Reserva> reservas = reservasDAO.buscaReservasPorHotel(hotel.getNombre());
        for (Reserva r : reservas) {
            reservasDAO.eliminaReserva(r);
        }
        hotelDAO.eliminaHotel(hotel);
    }
 
    private void guardaReserva(LocalDate entrada, LocalDate salida, int numHabitaciones) {
        DatosCliente cliente = new DatosCliente();
        cliente.setDni("00000000T");
        cliente.setNombre("Test");
        cliente.setPrimerApellido("Test");
        cliente.setEmail("test@test.es");
 
        DatosPago pago = new DatosPago();
        pago.setNumTarjeta("1234567890123456");
        pago.setCvc(123);
        pago.setMesCaducidad(12);
        pago.setAnhoCaducidad(2030);
        pago.setTipoTarjeta(TipoTarjeta.VISA);
 
        Reserva reserva = new Reserva();
        reserva.setFechaEntrada(entrada);
        reserva.setFechaSalida(salida);
        reserva.setHotel(hotel);
        reserva.setDatosCliente(cliente);
        reserva.setDatosPago(pago);
        reserva.getLineas().add(new LineaReserva(numHabitaciones, habDoble));
 
        assertTrue(reservasDAO.anhadeReserva(reserva));
    }
 
    @Test
    public void test01_iniciaReservaSinReservasAsociadas() {
        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);
 
        assertEquals(1, habitacionesDisp.size());
        assertEquals(5, habitacionesDisp.get(0).getDisponibles());
    }
 
    @Test
    public void test02_iniciaReservaConReservaSolapada() {
        guardaReserva(LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 16), 2);
 
        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);
 
        assertEquals(1, habitacionesDisp.size());
        assertEquals(3, habitacionesDisp.get(0).getDisponibles());
    }
 
    @Test
    public void test03_iniciaReservaConReservaNoSolapa() {
        guardaReserva(LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5), 1);
 
        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);
 
        assertEquals(1, habitacionesDisp.size());
        assertEquals(5, habitacionesDisp.get(0).getDisponibles());
    }
 
    @Test
    public void test04_iniciaReservaSinHabitacionesDisponibles() {
        guardaReserva(LocalDate.of(2025, 5, 9), LocalDate.of(2025, 5, 16), 5);
 
        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);
 
        assertTrue(habitacionesDisp.isEmpty());
    }
}
 