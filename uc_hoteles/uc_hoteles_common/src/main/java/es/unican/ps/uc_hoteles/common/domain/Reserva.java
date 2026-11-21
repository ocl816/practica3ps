package es.unican.ps.uc_hoteles.common.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity 
@Table(name = "Reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_res")
    private int id;

    @Column(name = "fech_ent", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fech_sal", nullable = false)
    private LocalDate fechaSalida;

    @ManyToOne
    @JoinColumn(name = "hotel_fk") 
    private Hotel hotel;
    
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "clien_fk") 
    private DatosCliente datosCliente;

    @Embedded 
    @Column(name = "dat_pag")
    private DatosPago datosPago;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reser_fk")
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
