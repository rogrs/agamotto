package br.com.rogrs.agamotto.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rogrs.agamotto.exception.ParseLineException;
import br.com.rogrs.agamotto.repository.UnidadeNegociosRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.LinhasArquivos;
import br.com.rogrs.agamotto.domain.UnidadeNegocios;
import br.com.rogrs.agamotto.domain.enumeration.TipoMarcacao;
import br.com.rogrs.agamotto.domain.enumeration.TipoRegistro;

@Service
@Slf4j
public class ReaderAFDTService  implements IArquivos {

	
	@Autowired
	private UnidadeNegociosRepository unidadeNegociosRepository;
	
	@Value("${import.files.enconding}")
	private String enconding;
	
	@Override
	public List<LinhasArquivos> execute(final Arquivos arquivo) throws IOException {
		log.info("Inicio de processamento do arquivo {} tipo {}", arquivo.getNome(), arquivo.getTipo());
		List<String> linhas = leitura(arquivo.getCaminho());
		List<LinhasArquivos> marcacoes = null;

		if (!linhas.isEmpty()) {
			log.info(linhas.get(0));
			//parseCabecalho(linhas.get(0).toString());
			linhas.remove(0);

			log.info("Removendo ultima linha {}", linhas.get(linhas.size() - 1));
			linhas.remove(linhas.size() - 1);

			marcacoes = readLines(arquivo, linhas);

		}

		log.info("Final da processamento do arquivo {}", arquivo.getNome());
		return marcacoes;
	}

	private UnidadeNegocios parseCabecalho(String linha) {
		log.info("Parse do cabecalho da  primeira linha {}", linha);
		
		
		
		if (linha.substring(9, 10).equals(TipoRegistro.CABECALHO.registro())) {
		   log.debug(TipoRegistro.CABECALHO.registro());
		   UnidadeNegocios unidadeNegocios = unidadeNegociosRepository.findByCnpj(linha.substring(11, 25));
		   if (unidadeNegocios == null) {
			   unidadeNegocios = new UnidadeNegocios();
		   }
		   log.debug(linha.substring(0, 9));
		   log.debug(linha.substring(10, 11));
		   unidadeNegocios.setCnpj(linha.substring(11, 25));
		   unidadeNegocios.setCei(linha.substring(25, 37));
		   unidadeNegocios.setRazaoSocial(linha.substring(37, 187).trim());
		   
		   log.debug(" Unidade Organizacional {}", unidadeNegocios);
		   
		   return unidadeNegociosRepository.save(unidadeNegocios);
		}
		return null;
		
	}

	private List<String> leitura(String caminho) throws IOException {

		List<String> lines = new ArrayList<>();

		File f = new File(caminho);

		lines = FileUtils.readLines(f, enconding);

		return lines;
	}

	private List<LinhasArquivos> readLines(final Arquivos arquivo, List<String> lines) {
		List<LinhasArquivos> list = new ArrayList<>();

		log.info("Total de linhas para parse {}", lines.size());
		arquivo.setTotalLinhas(lines.size());

		for (String line : lines) {

			try {
				LinhasArquivos marcacoes = parseLinesAFDT(arquivo, line);
				list.add(marcacoes);
			} catch (ParseLineException e) {
				log.error("Erro de parse na linha {} do arquivo {}", line, arquivo.getNome(), e);
			}
		}

		return list;
	}

	public LinhasArquivos parseLinesAFDT(Arquivos arquivo, String linha) {

		LinhasArquivos obj = new LinhasArquivos();
		obj.setArquivos(arquivo);
		obj.setLinha(linha.trim());

		try {
			obj.setNsr(linha.substring(0, 9).trim());
			
			if (linha.substring(9, 10).equals(TipoRegistro.AFDT_DETALHE.registro())) {
				obj.setTipoRegistro(TipoRegistro.AFDT_DETALHE);
				obj.setDataPonto(linha.substring(10, 18).trim());
				obj.setHoraPonto(linha.substring(18, 22).trim());
				obj.setPis(linha.substring(22, 34).trim());
				//obj.setTipoOperacao(trataTipoOperacao(linha.substring(22, 23)));
				log.info(String.format("Numero REP [ %s ]",linha.substring(34, 51)));
				obj.setTipoMarcacao(trataTipoMarcacao(linha.substring(51, 52)));
				log.info(String.format("Sequencial Empregado [ %s ]",linha.substring(52, 54)));
				log.info(String.format("Tipo de Registro [ %s ]",linha.charAt(54)));
				if (linha.charAt(54) == 'I') {
					log.info(String.format("Motivo [ %s ]",linha.substring(55, 155).trim()));
				}
			}
			
			
	
			log.debug(String.format("Parse na linha[ %s ], no arquivo [ %s ]", obj.getLinha(), arquivo.getNome()));

		} catch (Exception e) {
			throw new ParseLineException(String.format("Erro de parse na linha[ %s ], Tipo Operacao [ %s ] no arquivo [ %s ]",
					obj.getLinha(), obj.getTipoOperacao().toString(), arquivo.getNome()), e);
		}

		return obj;
	}

	private TipoMarcacao trataTipoMarcacao(String tipoMarcacao) {
		TipoMarcacao tipo = null;

		switch (tipoMarcacao) {
		case ("D"):
			tipo = TipoMarcacao.DESCONSIDERADO;
			break;
		case ("S"):
			tipo = TipoMarcacao.SAIDA;
			break;
		case ("E"):
			tipo = TipoMarcacao.ENTRADA;
			break;
		default:
			tipo = TipoMarcacao.UNKNOWN;
		}

		return tipo;
	}



}
