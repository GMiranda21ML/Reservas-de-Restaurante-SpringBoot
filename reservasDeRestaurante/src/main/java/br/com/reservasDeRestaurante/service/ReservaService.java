package br.com.reservasDeRestaurante.service;

import br.com.reservasDeRestaurante.dto.CriarReservaDTO;
import br.com.reservasDeRestaurante.dto.ListarReservaDTO;
import br.com.reservasDeRestaurante.model.Mesa;
import br.com.reservasDeRestaurante.model.Reserva;
import br.com.reservasDeRestaurante.model.Usuario;
import br.com.reservasDeRestaurante.model.enums.StatusReserva;
import br.com.reservasDeRestaurante.repository.MesaRepository;
import br.com.reservasDeRestaurante.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public ResponseEntity<Void> criarReserva(CriarReservaDTO criarReservaDTO, Usuario usuario) {

        Mesa mesa = validacoesDeReserva(criarReservaDTO);

        Reserva reserva = new Reserva(criarReservaDTO.dataReserva(), mesa, usuario);
        mesa.reservarMesa();
        reservaRepository.save(reserva);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    public ResponseEntity<List<ListarReservaDTO>> listarReserva(Usuario usuario) {
        List<Reserva> reservas = reservaRepository.findAllByUsuarioId(usuario.getId());

        if (reservas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Você ainda não possui reservas");
        }

        List<ListarReservaDTO> reservasDto = reservas.stream()
                .map(ListarReservaDTO::new)
                .toList();

        return ResponseEntity.ok().body(reservasDto);

    }

    @Transactional
    public ResponseEntity<Void> cancelarReserva(Long id, Usuario usuario) {
        Optional<Reserva> reserva = reservaRepository.findById(id);

        if (reserva.isPresent()) {
            Reserva reservaPresente = reserva.get();

            if (!reservaPresente.getUsuario().getId().equals(usuario.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            reservaPresente.cancelarReserva();
            reservaPresente.getMesa().disponibilizarMesa();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado uma reserva com este id");
        }

        return ResponseEntity.noContent().build();
    }

    private Mesa validacoesDeReserva(CriarReservaDTO criarReservaDTO) {
        boolean existeMesa = mesaRepository.existsById(criarReservaDTO.mesaId());

        if (!existeMesa) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe uma mesa com este ID");
        }

        Mesa mesa = mesaRepository.getReferenceById(criarReservaDTO.mesaId());

        if (criarReservaDTO.numeroPessoas() > mesa.getCapacidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número de pessoas maior do que a capacidade total da mesa");
        }

        LocalTime hora = criarReservaDTO.dataReserva().toLocalTime();
        if (hora.isBefore(LocalTime.of(10, 0)) || hora.isAfter(LocalTime.of(22, 0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservas só podem ser feitas entre 10h e 22h");
        }

        boolean mesaOcupada = reservaRepository.existsByMesaIdAndDataReservaAndStatusReserva(
                criarReservaDTO.mesaId(),
                criarReservaDTO.dataReserva(),
                StatusReserva.ATIVO
        );

        if (mesaOcupada) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A mesa já está reservada nesse horário");
        }

        return mesa;
    }


}
