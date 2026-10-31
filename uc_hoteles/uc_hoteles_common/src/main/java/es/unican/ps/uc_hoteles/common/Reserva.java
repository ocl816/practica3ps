package es.unican.ps.uc_hoteles.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class Reserva {
    private int id;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private double importe;

    private Hotel hotel;
    private DatosCliente datosCliente;
    private DatosPago datosPago;

    private List<LineaReserva> lineas = new ArrayList<>();

}
