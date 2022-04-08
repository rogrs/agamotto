package br.com.rogrs.agamotto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.UnidadeNegocios;


@SuppressWarnings("unused")
@Repository
public interface UnidadeNegociosRepository extends JpaRepository<UnidadeNegocios, Long> {

	public UnidadeNegocios findByCnpj(String substring);

}
