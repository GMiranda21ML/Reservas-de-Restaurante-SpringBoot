package br.com.reservasDeRestaurante.controller;

import br.com.reservasDeRestaurante.dto.CriarReservaDTO;
import br.com.reservasDeRestaurante.dto.ListarReservaDTO;
import br.com.reservasDeRestaurante.model.Usuario;
import br.com.reservasDeRestaurante.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Void> criarReserva(@RequestBody @Valid CriarReservaDTO criarReservaDTO, @AuthenticationPrincipal Usuario usuario) {
        return reservaService.criarReserva(criarReservaDTO, usuario);
    }

    @GetMapping
    public ResponseEntity<List<ListarReservaDTO>> listarReservas(@AuthenticationPrincipal Usuario usuario) {
        return reservaService.listarReserva(usuario);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        return reservaService.cancelarReserva(id);
    }
}
