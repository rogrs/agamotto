package br.com.rogrs.repository;
import br.com.rogrs.domain.UnidadeNegocios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnidadeNegocios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadeNegociosRepository extends JpaRepository<UnidadeNegocios, Long> {

}
