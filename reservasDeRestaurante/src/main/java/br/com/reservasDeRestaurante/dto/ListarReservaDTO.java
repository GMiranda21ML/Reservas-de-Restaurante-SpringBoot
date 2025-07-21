package br.com.reservasDeRestaurante.dto;

import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.model.Reserva;
import br.com.reservasDeRestaurante.model.enums.StatusReserva;

import java.time.LocalDateTime;

public record ListarReservaDTO(Long id,
                               DetalharMesaDTO mesa,
                               LocalDateTime dataReserva,
                               StatusReserva statusReserva) {

    public ListarReservaDTO(Reserva reserva) {
        this(reserva.getId(), new DetalharMesaDTO(reserva.getMesa()), reserva.getDataReserva(), reserva.getStatusReserva());
    }
}