package br.com.reservasDeRestaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome; // nome ou numero da mesa
    private Integer capacidade;
    @Enumerated(EnumType.STRING)
    private Status status;

}
