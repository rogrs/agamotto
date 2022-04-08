package br.com.rogrs.agamotto.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Colaboradores;


@SuppressWarnings("unused")
@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaboradores, Long> {
	
	
	public Colaboradores findByPis(String pis);
	
	public List<Colaboradores> findByAtivo(Boolean flag);
	//public Optional<Colaboradores> findByPis(String pis);
}
