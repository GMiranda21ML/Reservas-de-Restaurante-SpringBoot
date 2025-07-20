package br.com.reservasDeRestaurante.dto;

import br.com.reservasDeRestaurante.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarMesaDTO(
        @NotBlank
        String nome,
        @NotNull
        Integer capacidade,
        @NotNull
        Status status) {
}
