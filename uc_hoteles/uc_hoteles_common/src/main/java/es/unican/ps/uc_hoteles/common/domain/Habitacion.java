package es.unican.ps.uc_hoteles.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor 
@Entity 
@Table(name = "habitaciones")
public class Habitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_hab")
    private long id;

    @Column(name = "prec_noc", nullable = false)
    private double precioPorNoche;
    
    @Column(name = "hab_disp", nullable = false)
    private int disponibles;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoHabitacion tipo;

    public Habitacion(double precioPorNoche, int disponibles, TipoHabitacion tipo) {
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.tipo = tipo;
    }
}