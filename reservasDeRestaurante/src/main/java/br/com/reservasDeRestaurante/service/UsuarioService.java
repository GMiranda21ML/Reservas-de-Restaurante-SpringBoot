package br.com.reservasDeRestaurante.service;

import br.com.reservasDeRestaurante.dto.TokenJwtDTO;
import br.com.reservasDeRestaurante.dto.UsuarioCadastroDTO;
import br.com.reservasDeRestaurante.dto.UsuarioLoginDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.model.Usuario;
import br.com.reservasDeRestaurante.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<TokenJwtDTO> efetuarLogin(UsuarioLoginDTO dados) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }

    @Transactional
    public ResponseEntity<Void> efetuarCadastro(UsuarioCadastroDTO dados) {

        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um usuário com esse email.");
        }

        Usuario usuario = new Usuario(dados, passwordEncoder);
        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
