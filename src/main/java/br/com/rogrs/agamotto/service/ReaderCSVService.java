package br.com.rogrs.agamotto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.LinhasArquivos;

@Service
@Slf4j
public class ReaderCSVService implements IArquivos {

	@Value("${import.files.enconding}")
	private String enconding;

	@Override
	public List<LinhasArquivos> execute(final Arquivos arquivo) throws IOException {
		log.info("Inicio de processamento do arquivo {} tipo {}", arquivo.getNome(), arquivo.getTipo());

		List<LinhasArquivos> marcacoes = new ArrayList<>();

		File inputF = new File(arquivo.getCaminho());
		InputStream inputFS = new FileInputStream(inputF);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
		// skip the header of the csv
		marcacoes = br.lines().skip(4).map(mapToItem).collect(Collectors.toList());
		br.close();
		
		for (LinhasArquivos ob:marcacoes) {
			
			ob.setArquivos(arquivo);
		}

		log.info("Final da processamento do arquivo {}", arquivo.getNome());
		return marcacoes;
	}

	private Function<String, LinhasArquivos> mapToItem = (line) -> {
		  String[] p = line.split(",");
		  LinhasArquivos obj = new LinhasArquivos();
		 
		//  obj.setArquivos(arquivo);
			obj.setLinha(line.trim());
		 
			 return obj;
	};


}
