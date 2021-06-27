package utility;

import exceptions.IncorrectArgumentException;
import exceptions.ServerIsNotAvailableException;
import exceptions.UnknownCommandException;
import exceptions.ValidationException;
import request.AnswerReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for read commands from console and separate command name and its' arguments for invoker
 */
public class CommandReader {
    private final Console console;
    private final Invoker invoker;
    private final Pattern commandNamePattern;
    private final Pattern argPattern;
    private final AnswerReader answerReader;

    /**
     * @param console - is used to read commands from console
     * @param invoker - invoker which wil execute received commands
     */
    public CommandReader(Console console, Invoker invoker, AnswerReader answerReader) {
        this.console = console;
        this.invoker = invoker;
        this.answerReader = answerReader;
        commandNamePattern = Pattern.compile("^\\w+");
        argPattern = Pattern.compile("\\b(.*\\s*)*");
    }

    /**
     * Start reading loop
     * Loop reads commands and calls invoker
     * Loop is finished when input is empty or exit commands is called
     */
    public void activeMode() {
        String line;
        String command;
        String arg;
        do {
            line = console.readln();
            if (line != null) {
                Matcher matcher = commandNamePattern.matcher(line);
                if (matcher.find()) {
                    command = matcher.group();
                } else {
                    System.out.println("Input is not a command.");
                    continue;
                }
                line = line.substring(command.length());
                matcher = argPattern.matcher(line);
                if (matcher.find()) {
                    arg = matcher.group();
                } else {
                    arg = "";
                }
                if (command.equals("help")) {
                    try {
                        invoker.exe(command, arg);
                    } catch (UnknownCommandException | ValidationException exception) {
                        continue;
                    } catch (IncorrectArgumentException exception) {
                        System.out.println(exception.getMessage());
                        continue;
                    }
                } else {
                    try {
                        invoker.exe(command, arg);
                        answerReader.readAnswer();
                    } catch (UnknownCommandException | IncorrectArgumentException | ServerIsNotAvailableException exception) {
                        System.out.println(exception.getMessage());
                    } catch (ValidationException exception){
                        continue;
                    }
                }
            } else {
                System.out.println("Input is not a command.");
            }
        } while (!invoker.isStopRequested());
    }

}
