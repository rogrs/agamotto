package br.com.rogrs.agamotto.service;

import java.io.IOException;
import java.util.List;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.LinhasArquivos;

public interface IArquivos {
	
	public List<LinhasArquivos> execute(Arquivos arquivo) throws IOException;

}
