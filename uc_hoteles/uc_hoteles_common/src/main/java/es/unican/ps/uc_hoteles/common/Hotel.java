package es.unican.ps.uc_hoteles.common;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Hotel {
    private String nombre;
    private String direcicon;
    private String localidad;
    private Categoria categoria;
    private List<Habitacion> habitaciones = new ArrayList<>();
}
