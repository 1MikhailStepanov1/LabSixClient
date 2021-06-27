package utility;

import command.*;
import exceptions.IncorrectArgumentException;
import exceptions.UnknownCommandException;
import exceptions.ValidationException;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;


/**
 * This class contains map with commands which can be execute
 */
public class Invoker {
    private final HashMap<String, CommandInterface> commands;
    private boolean isStopRequested = false;
    private final Object allowedToStop = Exit.class;

    public Invoker() {
        commands = new HashMap<>();
    }

    /**
     * Initialize commands map
     */
    public void initMap(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        commands.put("info", new Info(datagramChannel, socketAddress, console));
        commands.put("show", new Show(datagramChannel, socketAddress, console));
        commands.put("add", new Add(datagramChannel, socketAddress, console));
        commands.put("update", new Update(datagramChannel, socketAddress, console));
        commands.put("remove_by_id", new RemoveById(datagramChannel, socketAddress, console));
        commands.put("clear", new Clear(datagramChannel, socketAddress, console));
        commands.put("exit", new Exit(this));
        commands.put("add_if_max", new AddIfMax(datagramChannel, socketAddress, console));
        commands.put("remove_greater", new RemoveGreater(datagramChannel, socketAddress, console));
        commands.put("remove_lower", new RemoveLower(datagramChannel, socketAddress, console));
        commands.put("group_counting_by_position", new GroupCountingByPosition(datagramChannel, socketAddress, console));
        commands.put("count_less_than_start_date", new CountLessThanStartDate(datagramChannel, socketAddress, console));
        commands.put("filter_greater_than_start_date", new FilterGreaterThanStartDate(datagramChannel, socketAddress, console));
        commands.put("execute_script", new ExecuteScript(datagramChannel, socketAddress, console));
        commands.put("help", new Help(commands, datagramChannel, socketAddress, console));
    }

    public void exe(String name, String arg) throws UnknownCommandException,IncorrectArgumentException, ValidationException{
        if (commands.containsKey(name)) {
            try {
                commands.get(name).exe(arg);
            } catch (IncorrectArgumentException e) {
                throw new IncorrectArgumentException("Incorrect argument.");
            }
        } else {
            throw new UnknownCommandException("Unknown command. PLease, try again.");
        }
    }

    public boolean isStopRequested() {
        return isStopRequested;
    }

    /**
     * Request stop of the program
     *
     * @param requester - is true, when program stops
     */
    public void requestExit(Object requester) {
        if (requester.getClass().equals(allowedToStop)) {
            isStopRequested = true;
            System.out.println("Client has been stopped.");
            System.exit(0);
        }
    }
}
