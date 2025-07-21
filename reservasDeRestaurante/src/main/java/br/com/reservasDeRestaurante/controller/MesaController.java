package br.com.reservasDeRestaurante.controller;

import br.com.reservasDeRestaurante.dto.AtualizarMesaDTO;
import br.com.reservasDeRestaurante.dto.CriarMesaDTO;
import br.com.reservasDeRestaurante.dto.DetalharMesaDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.model.Usuario;
import br.com.reservasDeRestaurante.repository.MesaRepository;
import br.com.reservasDeRestaurante.service.MesaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> criarMesa(@RequestBody @Valid CriarMesaDTO criarMesaDTO) {
        return mesaService.criarMesa(criarMesaDTO);
    }

    @GetMapping
    public ResponseEntity<List<DetalharMesaDTO>> listarMesas() {
        return mesaService.listarMesas();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DetalharMesaDTO> atualizarMesa(@PathVariable Long id, @RequestBody AtualizarMesaDTO atualizarMesaDTO) {
        return mesaService.atualizarMesa(id, atualizarMesaDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarMesa(@PathVariable Long id) {
        return mesaService.deletarMesa(id);
    }
}
