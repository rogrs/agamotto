package br.com.rogrs.repository;

import br.com.rogrs.domain.ControlePonto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ControlePonto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlePontoRepository extends JpaRepository<ControlePonto, Long> {
}
