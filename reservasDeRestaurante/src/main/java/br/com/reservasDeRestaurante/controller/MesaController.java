package br.com.reservasDeRestaurante.controller;

import br.com.reservasDeRestaurante.dto.CriarMesaDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.repository.MesaRepository;
import br.com.reservasDeRestaurante.service.MesaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @PostMapping
    public ResponseEntity<Void> criarMesa(@RequestBody @Valid CriarMesaDTO criarMesaDTO) {
        return mesaService.criarMesa(criarMesaDTO);
    }
}
