package es.unican.ps.uc_hoteles.common.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity 
@Table(name = "Hoteles")
public class Hotel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_hot")
    private long id;

    @Column(name = "nomb_hot", nullable = false)
    private String nombre;

    @Column(name = "direcc", nullable = false)
    private String direcicon;

    @Column(name = "localid", nullable = false)
    private String localidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "categ", nullable = false)
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_fk")
    private List<Habitacion> habitaciones = new ArrayList<>();
}
