package es.unican.ps.uc_hoteles.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class LineaReserva {
    private int numHabitaciones;

    private Habitacion habitacion;
}
