package br.com.rogrs.repository;
import br.com.rogrs.domain.Cargos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cargos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CargosRepository extends JpaRepository<Cargos, Long> {

}
