package com.neosavvy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils
{
    public static final char NUMBER_FORMAT_PLACEHOLDER = '#';

    public static String formatPhoneNumber(String format, String number, String ext) {
        StringBuilder builder = new StringBuilder();

        if (number == null) {
            return null;
        }

        if (format != null) {
            format = format.trim();
        }

        if (isEmpty(format)) {
            builder.append(number);
        }
        else {
            String strippedNumber = number.replaceAll("[^0-9]+", "");
            int numNumbers = countOccurrences(format, NUMBER_FORMAT_PLACEHOLDER);

            if (strippedNumber.length() >= numNumbers) {
                int numberIndex = 0;

                for (int i = 0; i < format.length(); i++) {
                    char formatChar = format.charAt(i);

                    if (formatChar == NUMBER_FORMAT_PLACEHOLDER) {
                        builder.append(strippedNumber.charAt(numberIndex++));
                    }
                    else {
                        builder.append(formatChar);
                    }
                }
            }
            else {
                builder.append(number);
            }
        }

        if (!isEmpty(ext)) {
            builder.append(" x");
            builder.append(ext);
        }

        return builder.toString();
    }

    /**
     * Formats a duration
     *
     * @param seconds The duration to be formatted in seconds.
     * @param format The format the output should be in.  This should be a string that contains hh, mm, and/or ss.
     *               For example, passing "hh hrs mm mins" would return something like "4 hrs 26 mins".
     * @return The formatted duration
     */
    public static String formatDuration(int duration, String format) {
        int hours = 0;
        int mins = 0;
        int seconds = duration;

        if (format.contains("hh")) {
            hours = duration / 3600;
            mins = duration % 3600 / 60;
            seconds = duration % 3600 % 60;
        }
        else if (format.contains("mm")) {
            mins = duration / 60;
            seconds = duration % 60;
        }

        return format.replace("hh", "" + hours).replace("mm", "" + mins).replace("ss", "" + seconds);
    }

    public static String formatDate(Date date, String pattern, boolean relative) {
        if (date == null) {
            return "";
        }

        if (relative) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Calendar now = Calendar.getInstance();
            final int today = now.get(Calendar.DAY_OF_YEAR);

            if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                if (today == calendar.get(Calendar.DAY_OF_YEAR)) {
                    return "Today";
                }
                else if (today - 1 == calendar.get(Calendar.DAY_OF_YEAR)) {
                    return "Yesterday";
                }
                else if (today + 1 == calendar.get(Calendar.DAY_OF_YEAR)) {
                    return "Tomorrow";
                }
            }
        }

        return new SimpleDateFormat(pattern).format(date);
    }

    public static boolean isEmpty(String format) {
        return format == null || format.length() == 0;
    }

    public static int countOccurrences(String source, char match) {
        int occurrences = 0;
        int index = -1;

        while ((index = source.indexOf(match, index + 1)) != -1) {
            occurrences++;
        }

        return occurrences;
    }

    // Converts a given index into an alphabetic representation similar to how
    // Excel labels columns (A,B,C,...,Z,AA,AB,...,etc.)
    public static String convertToAlphaIndex(int index) {
        int pos = 0;
        int sum = 0;
        String retval = "";

        int n = index;

        do {
            int v = (int)Math.floor( ( n - sum - 1 ) / Math.pow( 26, pos ) );
            retval = (char)( ( v % 26 ) + 65 ) + retval;
            pos++;

            sum = 0;
            for ( int i = 1; i <= pos; i++ ){
                sum = ( sum + 1 ) * 26;
            }
        } while ( n > sum );

        return retval;
    }

    // Converts a given alphabetic index into an numeric index similar to how
    // Excel labels columns (A=1,B=2,C=3,...,Z=26,AA=27,AB=28,...,etc.)
    public static int convertToNumericIndex(String alphaIndex) {
        int sum = 0;
        String retval = "";

        for (int i = 0; i < alphaIndex.length(); i++) {
            int pos = alphaIndex.length() - 1 - i;
            sum += ((int)alphaIndex.charAt(i)) - 65 + (int)Math.pow(26, pos);
            pos++;
        }

        return sum;
    }

    public static String xmlEscape(String s) {
        if (s == null) {
            return "";
        }

        s = s.replaceAll("&", "&amp;");
        s = s.replaceAll("\"", "&quot;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll("<", "&lt;");
        return s;
    }

    public static String uriEscape(String src) {
        if (src == null) {
            return "";
        }

        StringBuilder tmp = new StringBuilder();
        tmp.ensureCapacity(src.length() * 6);

        for (int i = 0; i < src.length(); i++) {
            char j = src.charAt(i);

            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j) || j == '.' || j == '_' || j == '-' || j == '*') {
                tmp.append(j);
            }
            else if (j <256) {
                tmp.append("%");
                if (j <16) {
                    tmp.append("0");
                }
                tmp.append(Integer.toString(j, 16));
            }
            else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }

        return tmp.toString();
    }

    public static String sqlLikeEscape(String src, char escapeChar) {
        return src.replace("_", escapeChar + "_").replace("%", escapeChar + "%");
    }

    /**
     * Converts the newlines in a string to only have \ns in them to avoid 
     * things interpreting the \r as an extra newline.
     *
     * @param src
     * @return
     */
    public static String convertNewLines(String src) {
        if (src == null) {
            return null;
        }

        return src.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
    }
}
