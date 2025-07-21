package br.com.reservasDeRestaurante.repository;

import br.com.reservasDeRestaurante.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    boolean existsByNome(String nome);

    List<Mesa> findAllByOrderById();
}
