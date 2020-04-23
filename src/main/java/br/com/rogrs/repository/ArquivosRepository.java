package br.com.rogrs.repository;

import br.com.rogrs.domain.Arquivos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Arquivos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArquivosRepository extends JpaRepository<Arquivos, Long> {
}
