package es.unican.ps.uc_hoteles.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity 
@Table(name = "Clientes")
public class DatosCliente {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cli")
    private long id;
    
    @Column(unique = true, nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "apll_1",nullable = false)
    private String primerApellido;

    @Column(name = "apll_2")
    private String segundoApellido;

    @Column(nullable = false)
    private String email;
}
