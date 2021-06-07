package util;

import client.Sender;
import command.CommandInvoker;
import command.CommandReceiver;
import command.commandClasses.*;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Print objects to console or read it with checking null value
 */
public class Console {
    private final Scanner scanner;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints toOut.toString() + \n to Console
     *
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
            System.exit(0);
            line = null;
        }
        if (line.length() == 0) {
            line = null;
        }
        return line;
    }

    public void activeMode(DatagramChannel datagramChannel, SocketAddress socketAddress) throws IOException {
        int delay = 60;
        Sender sender = new Sender(datagramChannel, socketAddress);
        WorkerFactory workerFactory = new WorkerFactory(1L);
        CommandInvoker commandInvoker = new CommandInvoker();
        CommandReceiver commandReceiver = new CommandReceiver(sender, datagramChannel, workerFactory, commandInvoker, delay);

        commandInvoker.addCommandToMap("add", new Add(commandReceiver));
        commandInvoker.addCommandToMap("add_if_max", new AddIfMax(commandReceiver));
        commandInvoker.addCommandToMap("clear", new Clear(commandReceiver));
        commandInvoker.addCommandToMap("count_less_than_start_date", new CountLessThanStartDate(commandReceiver));
        commandInvoker.addCommandToMap("exit", new Exit(commandReceiver));
        commandInvoker.addCommandToMap("filter_greater_than_start_date", new FilterGreaterThanStartDate(commandReceiver));
        commandInvoker.addCommandToMap("group_counting_by_position", new GroupCountingByPosition(commandReceiver));
        commandInvoker.addCommandToMap("help", new Help(commandReceiver));
        commandInvoker.addCommandToMap("info", new Info(commandReceiver));
        commandInvoker.addCommandToMap("remove_by_id", new RemoveById(commandReceiver));
        commandInvoker.addCommandToMap("remove_greater", new RemoveGreater(commandReceiver));
        commandInvoker.addCommandToMap("remove_lower", new RemoveLower(commandReceiver));
        commandInvoker.addCommandToMap("show", new Show(commandReceiver));
        commandInvoker.addCommandToMap("update", new Update(commandReceiver));

        while (scanner.hasNextLine()) {
            String line;
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException exception) {
                System.exit(0);
                line = null;
            }
            if (line.length() == 0) {
                line = null;
            }
            commandInvoker.executeCommand(line.trim().split(" "));
        }
    }
}
