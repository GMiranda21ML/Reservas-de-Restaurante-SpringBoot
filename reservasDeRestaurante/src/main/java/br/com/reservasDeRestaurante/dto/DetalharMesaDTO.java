package br.com.reservasDeRestaurante.dto;

import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.model.Status;

public record DetalharMesaDTO(Long id,
                              String nome,
                              Integer capacidade,
                              Status status) {

    public DetalharMesaDTO(Mesa mesa) {
        this(mesa.getId(), mesa.getNome(), mesa.getCapacidade(), mesa.getStatus());
    }
}
