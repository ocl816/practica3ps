package es.unican.ps.uc_hoteles.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  @NoArgsConstructor
@Entity 
@Table(name = "LineaReservas")
public class LineaReserva {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea")
    private long id;

    @Column(name = "num_hab", nullable = false)
    private int numHabitaciones;

    @ManyToOne 
    @JoinColumn(name = "habit_fk")
    private Habitacion habitacion;

    public LineaReserva(int numHabitaciones, Habitacion habitacion) {
        this.numHabitaciones = numHabitaciones;
        this.habitacion = habitacion;
    }
}
