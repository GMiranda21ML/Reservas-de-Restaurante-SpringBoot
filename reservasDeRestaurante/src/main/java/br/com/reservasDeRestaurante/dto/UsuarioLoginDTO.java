package br.com.reservasDeRestaurante.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginDTO(
        @NotBlank
        String email,
        @NotBlank
        String senha) {
}
