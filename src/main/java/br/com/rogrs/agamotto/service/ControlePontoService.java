package br.com.rogrs.agamotto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.rogrs.agamotto.repository.ControlePontoItemRepository;
import br.com.rogrs.agamotto.repository.ControlePontoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.domain.ControlePonto;
import br.com.rogrs.agamotto.domain.ControlePontoItem;

@Service
@Transactional
@Slf4j
public class ControlePontoService {

    private final ControlePontoRepository repository;

    private final ControlePontoItemRepository repositoryPontoItemRepository;

    @Autowired
    public ControlePontoService(ControlePontoRepository cp, ControlePontoItemRepository cpi) {
        this.repository = cp;
        this.repositoryPontoItemRepository = cpi;
    }

    @Transactional
    public ControlePonto createControlePonto(Colaboradores colaborador) throws IllegalArgumentException {

        if (null == colaborador) {
            throw new IllegalArgumentException(String.format("Colaborado não informado!"));
        }

        List<ControlePonto> controles = findByColaborador(colaborador);

        ControlePonto obj = null;
        if (controles.isEmpty()) {
            obj = new ControlePonto();
        } else {
            obj = controles.get(0);
        }

        obj.setColaborador(colaborador);

        return repository.save(obj);

    }

    public ControlePonto getControlePontoByColaborador(Colaboradores colaborador) {
        ControlePonto obj = null;

        List<ControlePonto> controles = findByColaborador(colaborador);


        if (controles.isEmpty()) {
            throw new IllegalArgumentException(String.format("Controle de ponto não foi encontrado para o Colaborador =[%s]!", colaborador.toString()));
        } else {

            obj = controles.get(0);
        }
        return obj;


    }

    public List<ControlePonto> findByColaborador(Colaboradores colaborador) {

        return repository.findByColaborador(colaborador);
    }

    public List<ControlePonto> findControlePontoAll() {
        return repository.findAll();
    }

    public Optional<ControlePonto> findControlePontoById(Long id) {
        return repository.findById(id);
    }

    public void saveControlePonto(ControlePonto obj) {
        repository.save(obj);
    }

    public void deleteControlePontoById(Long id) {
        repository.deleteById(id);
    }

    public List<ControlePontoItem> findControlePontoItemByControlePonto(ControlePonto controlePonto) {
        return repositoryPontoItemRepository.findByControlePonto(controlePonto);
    }

    @Transactional
    public void saveMarcacoesAll(List<ControlePontoItem> ajustes) {

        System.out.println(ajustes.size());
        repositoryPontoItemRepository.saveAll(ajustes);
        System.out.println("Concluido " + ajustes.size());
    }

}
