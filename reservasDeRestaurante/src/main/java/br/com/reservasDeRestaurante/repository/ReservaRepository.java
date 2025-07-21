package br.com.reservasDeRestaurante.repository;

import br.com.reservasDeRestaurante.model.Reserva;
import br.com.reservasDeRestaurante.model.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByMesaIdAndDataReservaAndStatusReserva(Long id, LocalDateTime dataReserva, StatusReserva statusReserva);

    List<Reserva> findAllByUsuarioId(Long id);
}
