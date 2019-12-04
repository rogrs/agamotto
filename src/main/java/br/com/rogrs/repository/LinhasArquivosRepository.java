package br.com.rogrs.repository;
import br.com.rogrs.domain.LinhasArquivos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LinhasArquivos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinhasArquivosRepository extends JpaRepository<LinhasArquivos, Long> {

}
