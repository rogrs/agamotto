package br.com.rogrs.repository;

import br.com.rogrs.domain.Colaboradores;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Colaboradores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaboradores, Long> {
}
