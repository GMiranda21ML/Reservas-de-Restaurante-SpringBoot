package br.com.reservasDeRestaurante.service;

import br.com.reservasDeRestaurante.dto.AtualizarMesaDTO;
import br.com.reservasDeRestaurante.dto.CriarMesaDTO;
import br.com.reservasDeRestaurante.dto.DetalharMesaDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.repository.MesaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public ResponseEntity<Void> criarMesa(CriarMesaDTO criarMesaDTO) {
        if (mesaRepository.existsByNome(criarMesaDTO.nome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma mesa com esse nome.");
        }

        Mesa mesa = new Mesa(criarMesaDTO);
        mesaRepository.save(mesa);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<DetalharMesaDTO>> listarMesas() {
        List<Mesa> mesas = mesaRepository.findAll();

        List<DetalharMesaDTO> lista = mesas.stream()
                .map(DetalharMesaDTO::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @Transactional
    public ResponseEntity<DetalharMesaDTO> atualizarMesa(Long id, AtualizarMesaDTO atualizarMesaDTO) {
        Optional<Mesa> mesa = mesaRepository.findById(id);

        if (mesa.isPresent()) {
            mesa.get().atualizarMesa(atualizarMesaDTO);
            return ResponseEntity.ok(new DetalharMesaDTO(mesa.get()));
        }

        return null;

    }

    @Transactional
    public ResponseEntity<Void> deletarMesa(Long id) {
        mesaRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
