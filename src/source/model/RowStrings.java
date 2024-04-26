package source.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Static class that extracts words and strings formatted for rows from an inputted text string.
 */

public class RowStrings {

    // Return ArrayList of words extracted from a text string.
    private static ArrayList<String> textToWords(String text) {
        ArrayList<String> words = new ArrayList<>();

        while (!text.isEmpty()) {
            // Remove white space.
            text = text.trim();
            if (text.isEmpty()) {
                break;
            }

            // Find next word according to  white space.
            int nextWhiteSpace = text.indexOf(' ');
            if (nextWhiteSpace == -1) {
                words.add(text);
                text = "";
            } else {
                words.add(text.substring(0, nextWhiteSpace));
                text = text.substring(nextWhiteSpace);
            }
        }

        return words;
    }

    // Center a string with spaces favoring the front.
    private static String centerString(String text, int rowSize) {
        text = text.trim();
        boolean addFront = true;

        while (text.length() < rowSize) {
            if (addFront) {
                text = " " + text;
                addFront = false;
            } else {
                text = text + " ";
                addFront = true;
            }
        }

        return text;
    }

    // Return ArrayList of strings extracted from a text string that fit a particular row size.
    public static List<String> textToRowStrings(String text, int rowSize) {
        ArrayList<String> words = textToWords(text);
        List<String> rows = new ArrayList<>();

        while (!words.isEmpty()) {
            String rowString = "";

            // Add full words.
            while (rowString.length() < rowSize) {
                if (words.isEmpty()) {
                    break;
                } else if (rowString.length() + words.getFirst().length() < rowSize) {
                    rowString = rowString + words.getFirst() + " ";
                    words.removeFirst();
                } else if (rowString.length() + words.getFirst().length() <= rowSize) {
                    rowString = rowString + words.getFirst();
                    words.removeFirst();
                    break;
                } else {
                    break;
                }
            }

            // Add split words.
            if (rowString.isEmpty() && !words.isEmpty()) {
                rowString = words.getFirst().substring(0, rowSize);
                words.set(0, words.getFirst().substring(rowSize + 1));
            }

            rows.add(rowString);
        }
        rows = rows.stream().map(row -> centerString(row, rowSize)).toList();
        return rows;
    }

}
