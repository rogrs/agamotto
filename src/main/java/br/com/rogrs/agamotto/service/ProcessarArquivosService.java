package br.com.rogrs.agamotto.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.domain.LinhasArquivos;
import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;
import br.com.rogrs.agamotto.repository.ArquivosRepository;
import br.com.rogrs.agamotto.repository.LinhasArquivosRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessarArquivosService {


	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ArquivosRepository arquivoRepository;

	@Autowired
	private LinhasArquivosRepository marcacoesRepository;

	@Autowired
	private ReaderAFDService readerAFD;

	@Autowired
	private ColaboradoresService colaboradoresService;

	@Autowired
	private ControlePontoService controlePontoService;

	@Autowired
	private ReaderAFDTService readerAFDT;

	@Autowired
	private ReaderCSVService readerCSV;

	public void execute() {

		List<Arquivos> arquivos = arquivoRepository.findByStatus(StatusSistema.CRIADO);
		if (arquivos == null || arquivos.isEmpty() ) {
			
		} else {
			for (Arquivos arquivo : arquivos) {
				log.debug("Leitura do arquivo = {} - inicio= {}", arquivo.getNome(), dateFormat.format(new Date()));
				try {
					List<LinhasArquivos> marcacoes = new ArrayList<>();

					switch (arquivo.getTipo()) {
					case "AFD":
						marcacoes = readerAFD.execute(arquivo);
						break;
					case "AFDT":
						marcacoes = readerAFDT.execute(arquivo);
						break;
					case "CSV":
						marcacoes = readerCSV.execute(arquivo);
						break;
					default:
						System.out.println("Não identificado");
					}

					marcacoesRepository.saveAll(processaMarcacoes(marcacoes));

					arquivo.setStatusProcessado();
				} catch (IOException e) {
					arquivo.setStatusErro();
					log.error("Erro na leitura do arquivo {} - message {}", arquivo.getNome(), e.getMessage(), e);

				} finally {
					arquivoRepository.saveAndFlush(arquivo);
					log.debug("Processamento  concluido do arquivo = {} - final= {}", arquivo.getNome(),
							dateFormat.format(new Date()));
				}

			}
		}
	}

	private List<LinhasArquivos> processaMarcacoes(List<LinhasArquivos> marcacoes) {
		marcacoesRepository.saveAll(marcacoes);
		log.debug("Processamento  marcacões importadas Total= {} ", marcacoes.size());

		for (LinhasArquivos obj : marcacoes) {

			if ((obj.getPis() != null)){
				Colaboradores colaborador = colaboradoresService.createColaboradorByPIS(obj);
				if (null != colaborador) {
					controlePontoService.createControlePonto(colaborador);
					obj.setColaborador(colaborador);
				}
				
				obj.setStatus(StatusSistema.PROCESSADO);
			} else {
				obj.setStatus(StatusSistema.NAO_PROCESSADO);
			}
			
		}
		
		return marcacoes;

	}
}