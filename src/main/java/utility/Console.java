package utility;

import request.AnswerReader;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Print objects to console or read it with checking null value
 */
public class Console {
    private final Scanner scanner;
    private static AnswerReader answerReader;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut - Object ot print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    public String readln() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException exception) {
            try {
                AnswerReader.stopRead();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
            line = null;
        }
        if (line.length() == 0) {
            line = null;
        }
        return line;
    }
}
