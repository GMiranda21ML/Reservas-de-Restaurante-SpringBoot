package br.com.reservasDeRestaurante.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CriarReservaDTO(
        @NotNull
        Long mesaId,
        @NotNull
        @Future
        LocalDateTime dataReserva,
        @NotNull
        Integer numeroPessoas) {
}
