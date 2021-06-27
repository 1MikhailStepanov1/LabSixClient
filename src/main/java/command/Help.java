package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

public class Help extends CommandAbstract{
    private final HashMap<String, CommandInterface> commands;
    private final Receiver receiver;
    public Help(HashMap<String, CommandInterface> commands, DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        super("Show allowed commands");
        this.commands = commands;
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException {
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            receiver.help(commands);
        }
    }
}
