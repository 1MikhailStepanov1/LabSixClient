package utility;

import command.CommandInterface;
import data.Worker;
import exceptions.*;
import request.AnswerReader;
import request.RequestSender;
import request.SerializationFromClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Receiver {
    private final WorkerFactory workerFactory = new WorkerFactory(1L);
    private final DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;
    private final RequestSender requestSender;
    private HashMap<String, CommandInterface> commands;
    private final HashSet<String> filePaths = new HashSet<>();
    private final Invoker invoker = new Invoker();
    private final AnswerReader answerReader;

    public Receiver(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
        requestSender = new RequestSender(datagramChannel, socketAddress);
        workerFactory.setConsole(console);
        answerReader = console.getAnswerReader();
    }


    public void help(HashMap<String, CommandInterface> commands) {
        this.commands = commands;
        commands.forEach((key, value) -> System.out.println(key + " - " + value.getDescription()));
    }

    public void add() {
        try {
            requestSender.sendRequest(new SerializationFromClient("add", null, workerFactory.getWorkerFromConsole()));
        } catch (NullFieldException | IncorrectValueException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void addIfMax() {
        try {
            requestSender.sendRequest(new SerializationFromClient("add_if_max", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void clear() {
        requestSender.sendRequest(new SerializationFromClient("clear", null, null));
    }

    public void countLessThanStartDate(String arg) {
        requestSender.sendRequest(new SerializationFromClient("count_less_than_start_date", arg, null));
    }

    public void filterGreaterThanStartDate(String arg) {
        requestSender.sendRequest(new SerializationFromClient("filter_greater_than_start_date", arg, null));
    }

    public void groupCountingByPosition() {
        requestSender.sendRequest(new SerializationFromClient("group_counting_by_position", null, null));
    }

    public void info() {
        requestSender.sendRequest(new SerializationFromClient("info", null, null));
    }

    public void removeById(String arg) {
        requestSender.sendRequest(new SerializationFromClient("remove_by_id", arg, null));
    }

    public void removeGreater() {
        try {
            requestSender.sendRequest(new SerializationFromClient("remove_greater", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void removeLower() {
        try {
            requestSender.sendRequest(new SerializationFromClient("remove_lower", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void show() {
        requestSender.sendRequest(new SerializationFromClient("show", null, null));
    }

    public void update(String arg) throws ValidationException{
        requestSender.sendRequest(new SerializationFromClient("validate_id", arg, null));
        try {
            if (answerReader.readValidation()) {
                try {
                    requestSender.sendRequest(new SerializationFromClient("update", arg, workerFactory.getWorkerFromConsole()));
                } catch (IncorrectValueException | NullFieldException e) {
                    System.out.println("Worker can't be created. Please, try again.");
                }
            } else throw new ValidationException();
        } catch (ServerIsNotAvailableException e) {
            System.out.println(e.getMessage());
        }

    }

    public void executeScript(String path) {
        String line;
        String command;
        String arg;
        AnswerReader answerReader = new AnswerReader(datagramChannel, socketAddress);
        Pattern commandNamePattern = Pattern.compile("^\\w+");
        Pattern argPattern = Pattern.compile("\\b(.*\\s*)*");
        ArrayList<String> parameters = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
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
                if (command.equals("add") || command.equals("update") || command.equals("remove_lower") || command.equals("remove_greater") || command.equals("add_if_max")) {
                    for (int i = 0; i < 9; i++) {
                        line = bufferedReader.readLine();
                        parameters.add(line);
                    }
                    Worker tempWorker = workerFactory.getWorkerFromScript(parameters);
                    requestSender.sendRequest(new SerializationFromClient(command, arg, tempWorker));
                } else if (command.equals("execute_script")) {
                    if (filePaths.add(arg)) {
                        filePaths.add(arg);
                    } else {
                        System.out.println("Recursion has been occurred. Please, correct your script.");
                    }
                } else if (command.equals("help")) {
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
                    } catch (ValidationException exception) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


