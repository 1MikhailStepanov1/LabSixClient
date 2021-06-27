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
    private AnswerReader answerReader;

    public Console(Scanner scanner, AnswerReader answerReader) {
        this.answerReader = answerReader;
        this.scanner = scanner;
    }
    public Console(Scanner scanner){
        this.scanner = scanner;
    }
    public AnswerReader getAnswerReader(){
        return answerReader;
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
                answerReader.stopRead();
            } catch (IOException e) {
                System.out.println("Client wasn't connected to the server.");
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
