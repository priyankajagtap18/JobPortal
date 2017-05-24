package com.jobportal.utils;

import java.text.DecimalFormat;

public class StringUtils {

    public String removeLettersFromString(String string) {
        return string.replaceAll("[^0-9]", "");
    }

    public String removeLettersExceptColonFromString(String string) {
        return string.replaceAll("[^0-9 :]", "");
    }

    public String removeNumbersFromString(String string) {
        return string.replaceAll("[^a-z A-Z]", "");
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        else if (str.equalsIgnoreCase("null"))
            return true;
        else
            return false;
    }

    public static String getCommaSeperatedPrice(String strPrice) {
        // convert string into long
        long lPrice = Long.parseLong(strPrice);
        // formatter
        DecimalFormat format5 = new DecimalFormat("##,###");
        DecimalFormat format7 = new DecimalFormat("##,##,###");
        DecimalFormat format9 = new DecimalFormat("##,##,##,###");
        DecimalFormat format11 = new DecimalFormat("##,##,##,##,###");
        DecimalFormat format13 = new DecimalFormat("##,##,##,##,##,###");
        // format number
        if (strPrice.length() <= 5) {
            return format5.format(lPrice);
        } else if (strPrice.length() <= 7) {
            return format7.format(lPrice);
        } else if (strPrice.length() <= 9) {
            return format9.format(lPrice);
        } else if (strPrice.length() <= 11) {
            return format11.format(lPrice);
        } else if (strPrice.length() <= 13) {
            return format13.format(lPrice);
        } else {
            return strPrice;
        }
    }

    public static String getCommaSeperatedPriceDouble(String strPrice) {
        // convert string into long
        double lPrice = Double.parseDouble(strPrice);
        // formatter
        DecimalFormat format5 = new DecimalFormat("##,###.##");
        DecimalFormat format7 = new DecimalFormat("##,##,###.##");
        DecimalFormat format9 = new DecimalFormat("##,##,##,###.##");
        DecimalFormat format11 = new DecimalFormat("##,##,##,##,###.##");
        DecimalFormat format13 = new DecimalFormat("##,##,##,##,##,###.##");
        // format number
        if (strPrice.length() <= 5) {
            return format5.format(lPrice);
        } else if (strPrice.length() <= 7) {
            return format7.format(lPrice);
        } else if (strPrice.length() <= 9) {
            return format9.format(lPrice);
        } else if (strPrice.length() <= 11) {
            return format11.format(lPrice);
        } else if (strPrice.length() <= 13) {
            return format13.format(lPrice);
        } else {
            return strPrice;
        }
    }

}
