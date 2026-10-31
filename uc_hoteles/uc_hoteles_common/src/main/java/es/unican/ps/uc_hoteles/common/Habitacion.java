package es.unican.ps.uc_hoteles.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class Habitacion {
    private double precioPorNoche;
    private int disponibles;
    private TipoHabitacion tipo;
}
