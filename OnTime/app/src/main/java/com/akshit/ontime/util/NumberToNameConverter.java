package com.akshit.ontime.util;

public class NumberToNameConverter {

    public static String convertNumber(final int number) {
        switch (number) {
            case 1:
                return "First";
            case 2:
                return "Second";
            case 3:
                return "Third";
            case 4:
                return "Fourth";
            case 5:
                return "Fifth";
            case 6:
                return "Sixth";
            case 7:
                return "Seventh";
            case 8:
                return "Eighth";
            default:
                return "Unknown";
        }
    }

}
