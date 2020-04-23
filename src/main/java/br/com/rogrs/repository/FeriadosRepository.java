package br.com.rogrs.repository;

import br.com.rogrs.domain.Feriados;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Feriados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeriadosRepository extends JpaRepository<Feriados, Long> {
}
