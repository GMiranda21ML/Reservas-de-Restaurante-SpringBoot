package br.com.reservasDeRestaurante.service;

import br.com.reservasDeRestaurante.dto.CriarMesaDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.repository.MesaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public ResponseEntity<Void> criarMesa(CriarMesaDTO criarMesaDTO) {
        Mesa mesa = new Mesa(criarMesaDTO);
        mesaRepository.save(mesa);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
