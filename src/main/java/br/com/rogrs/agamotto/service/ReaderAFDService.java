package br.com.rogrs.agamotto.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import br.com.rogrs.agamotto.domain.enumeration.TipoOperacao;
import br.com.rogrs.agamotto.domain.enumeration.TipoRegistro;

@Service
@Transactional
@Slf4j
public class ReaderAFDService implements IArquivos {

    @Autowired
    private UnidadeNegociosRepository unidadeNegociosRepository;

    @Value("${import.files.enconding}")
    private String enconding;

    @Override
    public List<LinhasArquivos> execute(final Arquivos arquivo) throws IOException {
        log.info("Inicio de processamento do arquivo {} tipo {}", arquivo.getNome(), arquivo.getTipo());
        List<String> linhas = leitura(arquivo.getCaminho());
        List<LinhasArquivos> marcacoes = new ArrayList<>();

        if (!linhas.isEmpty()) {

            marcacoes = readLines(arquivo, linhas);

        }

        log.info("Final da processamento do arquivo {}", arquivo.getNome());
        return marcacoes;
    }

    private UnidadeNegocios parseCabecalho(String linha) {
        log.info("Parse do cabecalho da  primeira linha {}", linha);

        String Cnpj = linha.substring(11, 25).trim();
        UnidadeNegocios unidadeNegocios = unidadeNegociosRepository.findByCnpj(Cnpj);
        if (unidadeNegocios == null) {
            unidadeNegocios = new UnidadeNegocios();
        }
        log.debug(linha.substring(0, 9));
        log.debug(linha.substring(10, 11));
        unidadeNegocios.setCnpj(Cnpj);
        unidadeNegocios.setCei(linha.substring(25, 37).trim());
        unidadeNegocios.setRazaoSocial(linha.substring(37, 187).trim());

        log.debug(" Unidade Organizacional {}", unidadeNegocios);

        return unidadeNegociosRepository.saveAndFlush(unidadeNegocios);
    }

    private void parseTrailer(String linha) {


    }


    private LinhasArquivos parseDados(LinhasArquivos obj, String linha) {

        TipoOperacao tipoOperacao = TipoOperacao.getTipoOperacao(linha.substring(22, 23));
        obj.setNsr(linha.substring(0, 9).trim());
        obj.setTipoOperacao(tipoOperacao);
        obj.setDataPonto(linha.substring(10, 18).trim());
        obj.setHoraPonto(linha.substring(18, 22).trim());

        return obj;
    }

    private List<String> leitura(String caminho) throws IOException {

        List<String> lines = new ArrayList<>();

        File f = new File(caminho);

        lines = FileUtils.readLines(f, enconding);

        return lines;
    }

    private List<LinhasArquivos> readLines(final Arquivos arquivo, List<String> lines) {

        log.info("Total de linhas para parse {}", lines.size());
        arquivo.setTotalLinhas(lines.size());

        List<LinhasArquivos> list = new ArrayList<>();

        for (String line : lines) {
            LinhasArquivos marcacoes = parseLinesAFD(arquivo, line);
            if (marcacoes != null) {
                list.add(marcacoes);
            }

        }

        return list;
    }

    public LinhasArquivos parseLinesAFD(Arquivos arquivo, String linha) {
        System.out.println(linha);
        LinhasArquivos obj = null;
        TipoRegistro tipoRegistro = null;
        try {

            tipoRegistro = TipoRegistro.getTipoRegistro(linha.substring(9, 10));
            if (tipoRegistro != null) {
                obj = new LinhasArquivos();
                obj.setArquivos(arquivo);
                obj.setLinha(linha.trim());

                obj.setTipoRegistro(tipoRegistro);
                System.out.println("Tipo registro >>>> " + tipoRegistro);


                switch (tipoRegistro) {
                    case CABECALHO:
                        parseCabecalho(linha);
                        break;
                    case INCLUSAO_ALTERACAO:
                        parseDados(obj, linha);
                        obj.setNomeEmpregado(linha.substring(49, 199).toUpperCase().trim());
                        break;
                    case MARCACAO_PONTO:
                        parseDados(obj, linha);
                        obj.setPis(linha.substring(22, 34).trim());
                        break;
                    case AJUSTE_TEMPO_REAL:
                        parseDados(obj, linha);
                        obj.setDataAjustada(linha.substring(22, 29));
                        obj.setHoraAjustada(linha.substring(29, 34));
                        break;
                    case INCLUSAO_ALTERACAO_EXCLUSAO:
                        parseDados(obj, linha);
                        obj.setPis(linha.substring(23, 35).trim());
                        obj.setNomeEmpregado(linha.substring(35, 87).toUpperCase().trim());
                        break;
                    case TRAILER:
                        parseTrailer(linha);
                        break;
                    default:
                        System.out.println("ERROR >>> " + linha.trim());
                }

                log.debug(String.format("Parse na linha[ %s ], Tipo Registro [ %s ] no arquivo [ %s ]", linha,
                        tipoRegistro, arquivo.getNome()));
            }

        } catch (Exception e) {
            throw new ParseLineException(
                    String.format("Erro de parse na linha[ %s ], Tipo Registro [ %s ] no arquivo [ %s ]",
                            linha, tipoRegistro, arquivo.getNome()),
                    e);
        }

        return obj;
    }


}
