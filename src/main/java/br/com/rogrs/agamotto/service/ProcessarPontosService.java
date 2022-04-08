package br.com.rogrs.agamotto.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.rogrs.agamotto.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.rogrs.agamotto.domain.ControlePontoItem;
import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.domain.ControlePonto;
import br.com.rogrs.agamotto.domain.LinhasArquivos;
import br.com.rogrs.agamotto.domain.enumeration.StatusSistema;
import br.com.rogrs.agamotto.domain.enumeration.TipoMotivoAjuste;
import br.com.rogrs.agamotto.repository.LinhasArquivosRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessarPontosService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final LinhasArquivosRepository linhasRepository;

    private final ControlePontoService controlePontoService;

    @Autowired
    public ProcessarPontosService(LinhasArquivosRepository lar, ControlePontoService cps) {
        this.linhasRepository = lar;
        this.controlePontoService = cps;
    }

    @Scheduled(fixedRate = 7000)
    public void reportCurrentTime() {

        List<LinhasArquivos> linhas = linhasRepository.findByStatus(StatusSistema.PROCESSADO);

        if (null == linhas || linhas.isEmpty()) {

        } else {
            log.debug("Processamento de marcações Total {} - inicio= {}", linhas.size(), dateFormat.format(new Date()));
            List<ControlePontoItem> ajustes = new ArrayList<>();
            for (LinhasArquivos linha : linhas) {

                Colaboradores colaborador = linha.getColaborador();
                if (colaborador != null) {
                    ControlePonto controlePonto = controlePontoService.getControlePontoByColaborador(colaborador);

                    if (null != controlePonto) {
                        ControlePontoItem obj = new ControlePontoItem();
                        obj.setControlePonto(controlePonto);
                        obj.setDataPonto(DateTimeUtils.strToLocalDate(linha.getDataPonto()));
                        obj.setHoraAjuste(DateTimeUtils.strToLocalTime(linha.getHoraPonto()));
                        obj.setMotivo(TipoMotivoAjuste.CONTROLE_PONTO);
                        ajustes.add(obj);
                        linha.setStatus(StatusSistema.CONCLUIDO);
                    } else {
                        linha.setStatus(StatusSistema.NAO_PROCESSADO);
                    }


                }
            }

            linhasRepository.saveAll(linhas);

            controlePontoService.saveMarcacoesAll(ajustes);
            log.debug("Processamento de marcações importadas - finalizado= {}", dateFormat.format(new Date()));


        }

    }

}