package br.com.rogrs.repository;
import br.com.rogrs.domain.Equipes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Equipes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipesRepository extends JpaRepository<Equipes, Long> {

}
