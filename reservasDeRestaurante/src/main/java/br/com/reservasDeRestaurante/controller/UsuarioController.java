package br.com.reservasDeRestaurante.controller;

import br.com.reservasDeRestaurante.dto.TokenJwtDTO;
import br.com.reservasDeRestaurante.dto.UsuarioCadastroDTO;
import br.com.reservasDeRestaurante.dto.UsuarioLoginDTO;
import br.com.reservasDeRestaurante.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwtDTO> efetuarLogin(@RequestBody @Valid UsuarioLoginDTO dados) {
        return usuarioService.efetuarLogin(dados);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> efetuarCadastro(@RequestBody @Valid UsuarioCadastroDTO dados) {
        return usuarioService.efetuarCadastro(dados);
    }

}
