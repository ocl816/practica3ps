package es.unican.ps.uc_hoteles.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class DatosPago {
    private String numTarjeta;
    private int cvc;
    private int mesCaducidad;
    private int anhoCaducidad;
}
