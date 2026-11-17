package es.unican.ps.uc_hoteles.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.unican.ps.uc_hoteles.common.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.common.domain.Habitacion;
import es.unican.ps.uc_hoteles.common.domain.Hotel;
import es.unican.ps.uc_hoteles.common.domain.LineaReserva;
import es.unican.ps.uc_hoteles.common.domain.Reserva;
import es.unican.ps.uc_hoteles.common.domain.TipoHabitacion;

public class GestionReservasUsuarioTest {

    @Mock 
    private IReservasDAO reservasDAO;

    @InjectMocks 
    private GestionReservasUsuario sut;

    private Hotel hotel;
    private Habitacion habDoble;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    @BeforeEach 
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        hotel = new Hotel();
        hotel.setNombre("Hotel");
        
        habDoble = new Habitacion(100.0, 5, TipoHabitacion.DOBLE);
        hotel.setHabitaciones(new ArrayList<>(Arrays.asList(habDoble)));

        fechaEntrada = LocalDate.of(2025, 5, 10);
        fechaSalida = LocalDate.of(2025, 5, 15);
    }

    @Test 
    public void test01_iniciaReservaSinReservasAsociadas() {
        when(reservasDAO.buscaReservasPorHotel("Hotel")).thenReturn(null);

        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);

        assertEquals(1, habitacionesDisp.size());
        assertEquals(5, habitacionesDisp.get(0).getDisponibles());
    }

    @Test 
    public void test02_iniciaReservaConReservaSolapada() {
        Reserva reservaSolapada = new Reserva();
        reservaSolapada.setFechaEntrada(LocalDate.of(2025, 5, 12));
        reservaSolapada.setFechaSalida(LocalDate.of(2025, 5, 16));
        reservaSolapada.setLineas(Arrays.asList(new LineaReserva(2, habDoble)));

        when(reservasDAO.buscaReservasPorHotel("Hotel")).thenReturn(Arrays.asList(reservaSolapada));

        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);

        assertEquals(1, habitacionesDisp.size());
        assertEquals(3, habitacionesDisp.get(0).getDisponibles());
    }

    @Test
    public void test03_iniciaReservaConReservaNoSolapa() {
        Reserva reservaAnterior = new Reserva();
        reservaAnterior.setFechaEntrada(LocalDate.of(2025, 5, 1));
        reservaAnterior.setFechaSalida(LocalDate.of(2025, 5, 5));

        when(reservasDAO.buscaReservasPorHotel("Hotel")).thenReturn(Arrays.asList(reservaAnterior));

        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);

        assertEquals(1, habitacionesDisp.size());
        assertEquals(5, habitacionesDisp.get(0).getDisponibles());
    }

    @Test 
    public void test04_iniciaReservaSinHabitacionesDisponibles() {
        Reserva reservaSolapada = new Reserva();
        reservaSolapada.setFechaEntrada(LocalDate.of(2025, 5, 9));
        reservaSolapada.setFechaSalida(LocalDate.of(2025, 5, 16));
        reservaSolapada.setLineas(Arrays.asList(new LineaReserva(5, habDoble)));

        when(reservasDAO.buscaReservasPorHotel("Hotel")).thenReturn(Arrays.asList(reservaSolapada));

        List<Habitacion> habitacionesDisp = sut.iniciarReserva(hotel, fechaEntrada, fechaSalida);

        assertTrue(habitacionesDisp.isEmpty());
    }
}
