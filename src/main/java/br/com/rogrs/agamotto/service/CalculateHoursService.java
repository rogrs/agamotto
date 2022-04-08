package br.com.rogrs.agamotto.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.rogrs.agamotto.domain.AgruparControlePontoItem;
import br.com.rogrs.agamotto.domain.ControlePonto;
import br.com.rogrs.agamotto.domain.ControlePontoItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculateHoursService {


    public Long calculate(LocalDateTime oldDate, LocalDateTime newDate) {

        log.debug("Executando oldDate=[{}]   newDate=[{}] ", oldDate, newDate);

        Long hours = ChronoUnit.HOURS.between(oldDate, newDate);
        log.debug("Resultado {} ", hours);
        return hours;
    }

    public LocalDate parseDate(String sDate) {
        LocalDate localDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return localDate;
    }

    public LocalTime parseHour(String sHour) {

        LocalTime localDateTime = LocalTime.parse(sHour, DateTimeFormatter.ofPattern("H[H]:mm:ss"));

        return localDateTime;
    }

    public List<AgruparControlePontoItem> subSum(List<ControlePontoItem> items) {

        List<AgruparControlePontoItem> retorno = new ArrayList<>();

        for (ControlePontoItem cpi : items) {
            AgruparControlePontoItem acpi = new AgruparControlePontoItem();
            acpi.setDate(cpi.getDataPonto());
            acpi.setItems(items);
            //	item.setDataPonto();
            //	item.setHoraAjuste();

            //LocalTime total1 = t2.minusHours(t1.getHour()).minusMinutes(t1.getMinute());
            //LocalTime total2 = t4.minusHours(t3.getHour()).minusMinutes(t3.getMinute());
            retorno.add(acpi);
        }


        return retorno;
    }


    public Map<LocalDate, Map<ControlePonto, LocalTime>> agrupamento(List<ControlePontoItem> items) {

        Map<LocalDate, Map<ControlePonto, LocalTime>> valorDaContaPorIdentificadorPorData = items.stream()
                .collect(Collectors.groupingBy(ControlePontoItem::getDataPonto,
                        Collectors.toMap(ControlePontoItem::getControlePonto, ControlePontoItem::getHoraAjuste)));
        return valorDaContaPorIdentificadorPorData;
    }


    public Map<LocalDate, List<ControlePontoItem>> agrupamentoByDataPonto(List<ControlePontoItem> items) {

        Map<LocalDate, List<ControlePontoItem>> groupByDataPonto = items.stream().collect(
                Collectors.groupingBy(ControlePontoItem::getDataPonto));
        return groupByDataPonto;
    }


    public String calculaSaldo(List<ControlePontoItem> items) {
        if (items.size() == 4) {


            LocalTime t1 = items.get(0).getHoraAjuste();
            LocalTime t2 = items.get(1).getHoraAjuste();
            LocalTime t3 = items.get(2).getHoraAjuste();
            LocalTime t4 = items.get(3).getHoraAjuste();

            LocalTime total1 = t2.minusHours(t1.getHour()).minusMinutes(t1.getMinute());
            LocalTime total2 = t4.minusHours(t3.getHour()).minusMinutes(t3.getMinute());

            LocalTime diff = total2.plusHours(total1.getHour()).plusMinutes(total1.getMinute());

            return String.format("%d:%02d:%02d",
                    diff.getHour(),
                    diff.getMinute(),
                    diff.getSecond());


        } else {
            return (format(0) + ":" + format(0) + ":" + format(0));
        }


    }

    private static String format(long s) {
        if (s < 10) return "0" + s;
        else return "" + s;
    }


}
