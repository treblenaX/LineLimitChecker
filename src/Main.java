/**
 * Author: Elbert Cheng
 * v1.0.0
 * 10/15/2019
 * Checks each line in a file to see if it exceeds a desired character limit.
 */

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Ask user for character limit input
            int characterLimit = askCharacterLimit(inputReader);

            // Ask user for the path
            File f = askFileInput(inputReader);

            // Analyze the file and go through it
            Map intList = analyzeFile(f, characterLimit);

            // Report the results to user
            reportIntList(intList, characterLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map analyzeFile(File f, int cLimit) throws IOException {
        System.out.println("Analyzing file...");
        System.out.println();

        BufferedReader reader = new BufferedReader(new FileReader(f));
        String s;

        Map<Integer, String> linesMap = new TreeMap<>();
        int lineNum = 1;


        // Change to YELLOW
        System.out.print(ANSI_YELLOW);
        while ((s = reader.readLine()) != null) {
            System.out.println(lineNum + ": " + s + " | Length: " + s.length());

            if (s.length() > cLimit) {
                linesMap.put(lineNum, s);
            }

            lineNum++;
        }
        // Color RESET
        System.out.print(ANSI_RESET);

        System.out.println();

        return linesMap;
    }

    private static void reportIntList(Map<Integer, String> map, int cLimit) {
        System.out.println("Lines that exceed the " + cLimit + " limit:");

        // Change color to RED
        System.out.print(ANSI_RED);
        for (int i : map.keySet()) {
            String line = map.get(i);

            System.out.println(i);
            System.out.println();
            System.out.println("Details:");
            System.out.println("\t\"" + line + "\"");
            System.out.println("\tLine Length: " + line.length());
            System.out.println("_________________________________");
            System.out.println();
        }
        System.out.print(ANSI_RESET);
    }

    //  *** User Input methods ***
    private static int askCharacterLimit(BufferedReader reader) throws IOException {
        System.out.println("What should the character limit be?");

        String s;
        int limit = -1;

        do {
            System.out.print("> ");

            if ((s = reader.readLine()) != null) {
                int temp = Integer.parseInt(s);

                if (temp > 0) {
                    limit = temp;
                }
            }
        } while (limit == -1);

        return limit;
    }

    private static File askFileInput(BufferedReader reader) throws IOException {
        System.out.println("What is the path of the file?");

        String s;
        File file = null;

        do {
            System.out.print("> ");

            if ((s = reader.readLine()) != null) {
                file = new File(s);
            }
        } while (file == null || !file.exists());

        return null;
    }
}
