package br.com.rogrs.agamotto.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.domain.ControlePonto;


@SuppressWarnings("unused")
@Repository
public interface ControlePontoRepository extends JpaRepository<ControlePonto, Long> {
	
	public List<ControlePonto> findByColaborador(Colaboradores colaborador);

}
