package br.com.reservasDeRestaurante.model;

import br.com.reservasDeRestaurante.dto.CriarReservaDTO;
import br.com.reservasDeRestaurante.model.enums.StatusReserva;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Mesa mesa;
    private LocalDateTime dataReserva;
    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    public Reserva() {}

    public Reserva(LocalDateTime dataReserva, Mesa mesa, Usuario usuario) {
        this.id = null;
        this.usuario = usuario;
        this.mesa = mesa;
        this.dataReserva = dataReserva;
        this.statusReserva = StatusReserva.ATIVO;
    }

    public Long getId() {
        return this.id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Mesa getMesa() {
        return this.mesa;
    }

    public LocalDateTime getDataReserva() {
        return this.dataReserva;
    }

    public StatusReserva getStatusReserva() {
        return this.statusReserva;
    }

    public void cancelarReserva() {
        this.statusReserva = StatusReserva.CANCELADO;
    }
}
