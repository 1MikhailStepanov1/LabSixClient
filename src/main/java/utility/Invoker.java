package utility;

import command.*;

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
    public void initMap(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        commands.put("info", new Info(datagramChannel, socketAddress));
        commands.put("show", new Show(datagramChannel, socketAddress));
        commands.put("add", new Add(datagramChannel, socketAddress));
        commands.put("update", new Update(datagramChannel, socketAddress));
        commands.put("remove_by_id", new RemoveById(datagramChannel, socketAddress));
        commands.put("clear", new Clear(datagramChannel, socketAddress));
        commands.put("exit", new Exit(this));
        commands.put("add_if_max", new AddIfMax(datagramChannel, socketAddress));
        commands.put("remove_greater", new RemoveGreater(datagramChannel, socketAddress));
        commands.put("remove_lower", new RemoveLower(datagramChannel, socketAddress));
        commands.put("group_counting_by_position", new GroupCountingByPosition(datagramChannel, socketAddress));
        commands.put("count_less_than_start_date", new CountLessThanStartDate(datagramChannel, socketAddress));
        commands.put("filter_greater_than_start_date", new FilterGreaterThanStartDate(datagramChannel, socketAddress));
        commands.put("execute_script", new ExecuteScript(datagramChannel, socketAddress));
        commands.put("help", new Help(commands, datagramChannel, socketAddress));
    }

    public void exe(String name, String arg) {
        if (commands.containsKey(name)) {
            commands.get(name).exe(arg);
        } else {
            System.out.println("It is not a command. Please try again.");
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
