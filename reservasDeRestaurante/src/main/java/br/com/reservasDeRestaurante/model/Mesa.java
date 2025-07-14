package br.com.reservasDeRestaurante.model;

import br.com.reservasDeRestaurante.dto.AtualizarMesaDTO;
import br.com.reservasDeRestaurante.dto.CriarMesaDTO;
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

    public Mesa() {}

    public Mesa(CriarMesaDTO criarMesaDTO) {
        this.id = null;
        this.nome = criarMesaDTO.nome();
        this.capacidade = criarMesaDTO.capacidade();
        this.status = criarMesaDTO.status();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public Status getStatus() {
        return status;
    }

    public void atualizarMesa(AtualizarMesaDTO atualizarMesaDTO) {
        if (atualizarMesaDTO.nome() != null) {
            this.nome = atualizarMesaDTO.nome();
        }

        if (atualizarMesaDTO.capacidade() != null) {
            this.capacidade = atualizarMesaDTO.capacidade();
        }

        if (atualizarMesaDTO.status() != null) {
            this.status = atualizarMesaDTO.status();
        }
    }
}
