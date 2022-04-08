package br.com.rogrs.agamotto.service;

import javax.transaction.Transactional;

import br.com.rogrs.agamotto.repository.ColaboradoresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.domain.LinhasArquivos;

@Service
@Transactional
@Slf4j
public class ColaboradoresService {

	private final ColaboradoresRepository repository;

	@Autowired
	public ColaboradoresService(ColaboradoresRepository colaboradoresRepository) {
		this.repository = colaboradoresRepository;
	}

	@Transactional
	public Colaboradores createColaboradorByPIS(LinhasArquivos linha) throws IllegalArgumentException {

		if (null == linha.getPis()) {
			throw new IllegalArgumentException(String.format("PIS nao informado na linha [ %s ]", linha));
		}

		Colaboradores obj = findByPis(linha.getPis());

		if (null == obj) {
			obj = new Colaboradores(linha.getPis());
		} else {

			if (null != linha.getNomeEmpregado()) {
				obj.setNome(linha.getNomeEmpregado());
			}
			obj.setPis(linha.getPis());
			if (null != linha.getNsr()) {
				obj.setMatricula(linha.getNsr());
			}
		}

		return repository.save(obj);

	}

	public Colaboradores findByPis(String pis) {

		return repository.findByPis(pis);
	}

}
