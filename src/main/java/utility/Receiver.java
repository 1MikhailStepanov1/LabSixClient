package utility;

import command.CommandInterface;
import data.Worker;
import exceptions.IncorrectValueException;
import exceptions.NullFieldException;
import request.RequestSender;
import request.Serialization;

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
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private final RequestSender requestSender = new RequestSender(datagramChannel, socketAddress);
    private HashMap<String, CommandInterface> commands;
    private HashSet<String> filePaths = new HashSet<>();
    private Invoker invoker = new Invoker();

    public Receiver(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
    }


    public void help(HashMap<String, CommandInterface> commands) {
        this.commands = commands;
        commands.entrySet().forEach((command) -> System.out.println(command.getKey() + " - " + command.getValue().getDescription()));
    }

    public void add() {
        try {
            requestSender.sendRequest(new Serialization("add", null, workerFactory.getWorkerFromConsole()));
        } catch (NullFieldException | IncorrectValueException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void addIfMax() {
        try {
            requestSender.sendRequest(new Serialization("add_if_max", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void clear() {
        requestSender.sendRequest(new Serialization("clear", null, null));
    }

    public void countLessThanStartDate(String arg) {
        requestSender.sendRequest(new Serialization("count_less_than_start_date", arg, null));
    }

    public void filterGreaterThanStartDate(String arg) {
        requestSender.sendRequest(new Serialization("filter_greater_than_start_date", arg, null));
    }

    public void groupCountingByPosition() {
        requestSender.sendRequest(new Serialization("group_counting_by_position", null, null));
    }

    public void info() {
        requestSender.sendRequest(new Serialization("info", null, null));
    }

    public void removeById(String arg) {
        requestSender.sendRequest(new Serialization("remove_by_id", arg, null));
    }

    public void removeGreater() {
        try {
            requestSender.sendRequest(new Serialization("remove_greater", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void removeLower() {
        try {
            requestSender.sendRequest(new Serialization("remove_lower", null, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void show() {
        requestSender.sendRequest(new Serialization("show", null, null));
    }

    public void update(String arg) {
        try {
            requestSender.sendRequest(new Serialization("update", arg, workerFactory.getWorkerFromConsole()));
        } catch (IncorrectValueException | NullFieldException e) {
            System.out.println("Worker can't be created. Please, try again.");
        }
    }

    public void executeScript(String path) {
        String line;
        String command;
        String arg;
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
                    requestSender.sendRequest(new Serialization(command, arg, tempWorker));
                } else if (command.equals("execute_script")) {
                    if (filePaths.add(arg)) {
                        filePaths.add(arg);
                    } else {
                        System.out.println("Recursion has been occurred. Please, correct your script.");
                    }
                } else {
                    invoker.exe(command, arg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


