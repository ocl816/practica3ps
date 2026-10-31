package es.unican.ps.uc_hoteles.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class Habitacion {
    private double precioPorNoche;
    private int disponibles;
    private TipoHabitacion tipo;
}
