package es.unican.ps.uc_hoteles.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data 
@Embeddable 
public class DatosPago {
    @Column(name= "num_tar") 
    private String numTarjeta;

    @Column(name= "cvc") 
    private int cvc;

    @Column(name= "mes_cad") 
    private int mesCaducidad;

    @Column(name= "anho_cad") 
    private int anhoCaducidad;
}
