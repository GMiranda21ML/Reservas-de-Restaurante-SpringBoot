package br.com.reservasDeRestaurante.dto;

import br.com.reservasDeRestaurante.model.Status;

public record DetalharMesa(Long id,
                           String nome,
                           Integer capacidade,
                           Status status) {
}
