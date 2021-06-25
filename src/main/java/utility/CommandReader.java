package utility;

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
            Matcher matcher = commandNamePattern.matcher(line);
            if (matcher.find()){
                command = matcher.group();
            }else{
                System.out.println("Input is not a command.");
                continue;
            }
            line = line.substring(command.length());
            matcher = argPattern.matcher(line);
            if (matcher.find()){
                arg = matcher.group();
            } else {
                arg = "";
            }
            invoker.exe(command, arg);
            answerReader.readAnswer();
        } while (!invoker.isStopRequested());
    }

}
