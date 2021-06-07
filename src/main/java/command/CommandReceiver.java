package command;

import client.Sender;
import command.commandClasses.*;
import data.Worker;
import util.WorkerFactory;
import util.exceptions.IncorrectValueException;
import util.exceptions.NullFieldException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashSet;

public class CommandReceiver {
    private final Sender sender;
    private final DatagramChannel datagramChannel;
    private final WorkerFactory workerFactory;
    private final CommandInvoker commandInvoker;
    private final Integer delay;
    private HashSet<String> scriptPaths;

    public CommandReceiver(Sender sender, DatagramChannel datagramChannel, WorkerFactory workerFactory, CommandInvoker commandInvoker, Integer delay) {
        this.sender = sender;
        this.datagramChannel = datagramChannel;
        this.workerFactory = workerFactory;
        this.commandInvoker = commandInvoker;
        this.delay = delay;
    }

    public void add() throws IOException, InterruptedException {
        try {
            sender.sendObject(new SerializeCommand(new Add(), workerFactory.getWorkerFromConsole()));
            Thread.sleep(delay);
        } catch (NullFieldException | IncorrectValueException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addIfMax() throws IOException, InterruptedException {
        try {
            sender.sendObject(new SerializeCommand(new AddIfMax(), workerFactory.getWorkerFromConsole()));
            Thread.sleep(delay);

        } catch (NullFieldException | IncorrectValueException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void clear() throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new Clear()));
        Thread.sleep(delay);
    }

    public void countLessThanStartDate(String arg) throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new CountLessThanStartDate(), arg));
        Thread.sleep(delay);
    }

    public void exit() throws IOException {
        datagramChannel.close();
        System.out.println("Client has been stopped.");
        System.exit(0);
    }

    public void filterGreaterThanStartDate(String arg) throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new FilterGreaterThanStartDate(), arg));
        Thread.sleep(delay);
    }

    public void groupCountingByPosition(String arg) throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new GroupCountingByPosition(), arg));
        Thread.sleep(delay);
    }

    public void help() {
        commandInvoker.getCommandMap().forEach((commandName, command) -> command.writeInfo());
    }

    public void info() throws IOException, InterruptedException, ClassNotFoundException {
        sender.sendObject(new SerializeCommand(new Info()));
        Thread.sleep(delay);
    }

    public void removeById(String arg) throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new RemoveById(), arg));
        Thread.sleep(delay);
    }

    public void removeGreater() throws IOException, InterruptedException {
        try {
            sender.sendObject(new SerializeCommand(new RemoveGreater(), workerFactory.getWorkerFromConsole()));
            Thread.sleep(delay);
        } catch (NullFieldException | IncorrectValueException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void removeLower() throws IOException, InterruptedException {
        try {
            sender.sendObject(new SerializeCommand(new RemoveLower(), workerFactory.getWorkerFromConsole()));
            Thread.sleep(delay);
        } catch (NullFieldException | IncorrectValueException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void show() throws IOException, InterruptedException {
        sender.sendObject(new SerializeCommand(new Show()));
        Thread.sleep(delay);
    }

    public void update(String arg) throws IOException, InterruptedException {
        try {
            sender.sendObject(new SerializeCommand(new Update(), arg, workerFactory.getWorkerFromConsole()));
            Thread.sleep(delay);
        } catch (NullFieldException | IncorrectValueException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void executeScript(String path) {
        String line;
        String command;
        ArrayList<String> parameters = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.split(" ")[0].matches("add|update|remove_lower|remove_greater|add_if_max")) {
                    command = line;
                    for (int i = 0; i < 9; i++) {
                        line = bufferedReader.readLine();
                        parameters.add(line);
                    }
                    Worker tempWorker = workerFactory.getWorkerFromScript(parameters);
                    if (tempWorker != null) {
                        switch (command.split(" ")[0]) {
                            case "add":
                                sender.sendObject(new SerializeCommand(new Add(), tempWorker));
                                Thread.sleep(delay);
                                break;
                            case "update":
                                sender.sendObject(new SerializeCommand(new Update(), command.split(" ")[1], tempWorker));
                                Thread.sleep(delay);
                                break;
                            case "remove_lower":
                                sender.sendObject(new SerializeCommand(new RemoveLower(), tempWorker));
                                Thread.sleep(delay);
                                break;
                            case "remove_greater":
                                sender.sendObject(new SerializeCommand(new RemoveGreater(), tempWorker));
                                Thread.sleep(delay);
                                break;
                            case "add_if_max":
                                sender.sendObject(new SerializeCommand(new AddIfMax(), tempWorker));
                                Thread.sleep(delay);
                                break;
                        }
                    } else if (line.split(" ")[0].equals("execute_script")) {
                        if (scriptPaths.add(line.split(" ")[1])) {
                            scriptPaths.add(line.split(" ")[1]);
                        } else {
                            System.out.println("Recursion has been occurred. Please, correct your script.");
                        }
                    }
                } else {
                    commandInvoker.executeCommand(line.split(" "));
                }
            }
        } catch (IOException | InterruptedException exception) {
            System.out.println("Something went wrong. " + exception.getMessage());
        }
    }
}

