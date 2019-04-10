package com.oritmalki.countries;

import com.oritmalki.countries.network.responses.RespCountry;

public class Utils {

    public static String formatSubtitle(RespCountry country) {
        String region = country.getRegion();
        String subregion = country.getSubregion();
        String capital = country.getCapital();
        String nativeName = country.getNativeName().isEmpty() ? "" : String.format(("(%s)"), country.getNativeName());

        String[] subtitleWords = {region, subregion, capital, nativeName};

        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (int i=0; i<subtitleWords.length-1; i++) {
            sb.append(separator);
            sb.append(subtitleWords[i]);
            separator = subtitleWords[i+1].isEmpty() ? "" : ", ";
        }
        sb.append(" ");
        sb.append(nativeName);
        sb.trimToSize();
        return sb.toString();
    }

}
