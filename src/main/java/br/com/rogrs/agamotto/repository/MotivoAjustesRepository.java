package br.com.rogrs.agamotto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.MotivoAjustes;


@SuppressWarnings("unused")
@Repository
public interface MotivoAjustesRepository extends JpaRepository<MotivoAjustes, Long> {

}
