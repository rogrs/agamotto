package br.com.rogrs.repository;

import br.com.rogrs.domain.Departamentos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Departamentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartamentosRepository extends JpaRepository<Departamentos, Long> {
}
