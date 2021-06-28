package utility;

import command.*;
import exceptions.IncorrectArgumentException;
import exceptions.UnknownCommandException;
import exceptions.ValidationException;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.HashSet;


/**
 * This class contains map with commands which can be execute
 */
public class Invoker {
    private final HashMap<String, CommandInterface> commands;
    private boolean isStopRequested = false;
    private final Object allowedToStop = Exit.class;
    private HashSet<String> filePaths = new HashSet<>();

    public Invoker() {
        commands = new HashMap<>();
    }

    /**
     * Initialize commands map
     */
    public void initMap(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console, Invoker invoker) {
        commands.put("info", new Info(datagramChannel, socketAddress, console, invoker));
        commands.put("show", new Show(datagramChannel, socketAddress, console, invoker));
        commands.put("add", new Add(datagramChannel, socketAddress, console, invoker));
        commands.put("update", new Update(datagramChannel, socketAddress, console, invoker));
        commands.put("remove_by_id", new RemoveById(datagramChannel, socketAddress, console, invoker));
        commands.put("clear", new Clear(datagramChannel, socketAddress, console, invoker));
        commands.put("exit", new Exit(invoker));
        commands.put("add_if_max", new AddIfMax(datagramChannel, socketAddress, console, invoker));
        commands.put("remove_greater", new RemoveGreater(datagramChannel, socketAddress, console, invoker));
        commands.put("remove_lower", new RemoveLower(datagramChannel, socketAddress, console, invoker));
        commands.put("group_counting_by_position", new GroupCountingByPosition(datagramChannel, socketAddress, console, invoker));
        commands.put("count_less_than_start_date", new CountLessThanStartDate(datagramChannel, socketAddress, console, invoker));
        commands.put("filter_greater_than_start_date", new FilterGreaterThanStartDate(datagramChannel, socketAddress, console, invoker));
        commands.put("execute_script", new ExecuteScript(datagramChannel, socketAddress, console, invoker));
        commands.put("help", new Help(commands, datagramChannel, socketAddress, console, invoker));
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

    public HashSet<String> getFilePaths(){
        return filePaths;
    }
    public void setFilePaths(HashSet<String> filePaths){
        this.filePaths = filePaths;
    }
    public void addPath(String path){
        filePaths.add(path);
    }
}
