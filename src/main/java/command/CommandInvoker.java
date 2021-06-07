package command;

import java.io.IOException;
import java.util.HashMap;

public class CommandInvoker {
    private final HashMap<String, CommandAbstract> commandMap = new HashMap<>();

    public void addCommandToMap(String commandName, CommandAbstract command) {
        commandMap.put(commandName, command);
    }

    public void executeCommand(String[] commandName) {
        try {
            if (commandName.length > 0 && !commandName[0].equals("")) {
                CommandAbstract command = commandMap.get(commandName[0]);
                command.execute(commandName);
            } else {
                System.out.println("Input is not a command.");
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            if (e.getMessage().equals("Connection reset by peer.")) {
                System.out.println("Server connection error.");
                System.exit(0);
            }
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, CommandAbstract> getCommandMap() {
        return commandMap;
    }
}
