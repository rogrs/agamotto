package br.com.rogrs.agamotto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;

@Repository
public interface ArquivosRepository  extends JpaRepository<Arquivos, Long> {
	public List<Arquivos> findByStatus(StatusSistema status);
	
	public Arquivos findByNome(String nome);

}
