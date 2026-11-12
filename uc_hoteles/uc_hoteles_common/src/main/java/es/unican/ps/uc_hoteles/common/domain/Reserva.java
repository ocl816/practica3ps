package es.unican.ps.uc_hoteles.common.domain;

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

    private Hotel hotel;
    private DatosCliente datosCliente;
    private DatosPago datosPago;

    private List<LineaReserva> lineas = new ArrayList<>();

    /**
     * Obtiene el importe de la reserva
     * @return el importe de la reserva
     */
    public double getImporte() {
        double res = 0;
        for (LineaReserva lineaReserva : lineas) {
            res += lineaReserva.getHabitacion().getPrecioPorNoche() * lineaReserva.getNumHabitaciones();
        }
        return res;
    }

}
