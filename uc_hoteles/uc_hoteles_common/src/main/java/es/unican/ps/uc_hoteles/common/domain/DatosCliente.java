package es.unican.ps.uc_hoteles.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class DatosCliente {
    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
}
