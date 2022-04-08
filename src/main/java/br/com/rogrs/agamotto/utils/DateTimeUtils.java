package br.com.rogrs.agamotto.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static LocalDate strToLocalDate(String sDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDate localDate = LocalDate.parse(sDate, formatter);
        return localDate;

    }

    public static LocalTime strToLocalTime(String sDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

        LocalTime localDate = LocalTime.parse(sDate, formatter);
        return localDate;

    }
}
