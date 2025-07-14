package br.com.reservasDeRestaurante.dto;

import br.com.reservasDeRestaurante.model.Status;

public record AtualizarMesaDTO(String nome, Integer capacidade, Status status) {
}
