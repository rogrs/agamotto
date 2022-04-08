package br.com.rogrs.agamotto.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsUtils {
	

	public static String rmAllSpecialCharacters(String c) {
		
		Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match= pt.matcher(c);
        while(match.find())
        {
            String s= match.group();
        c=c.replaceAll("\\"+s, "");
        }
		
		return c;
	}

}
