package br.com.rogrs.agamotto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.LinhasArquivos;
import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;

@Repository
public interface LinhasArquivosRepository  extends JpaRepository<LinhasArquivos, Long> {

	List<LinhasArquivos> findByArquivos(Arquivos nome);
	List<LinhasArquivos> findByStatus(StatusSistema status);

	List<LinhasArquivos> findByPis(String pis);

}
