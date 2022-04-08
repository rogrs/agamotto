package br.com.rogrs.agamotto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgruparControlePontoItem {

    private Long id;

    private LocalDate date;

    private String descricaoPeriodo;

    private Long sum;

    private List<ControlePontoItem> items = new ArrayList<>();

    private String valor;

    
}
