package br.com.extraplays.extracash.utils;

import java.text.DecimalFormat;

public class NumberUtil {

    private static final DecimalFormat numberFormat = new DecimalFormat("#.00");

    public static String format(Double amount){
        return numberFormat.format(amount);
    }

}
