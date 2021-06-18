package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

public class Help extends CommandAbstract{
    private final HashMap<String, CommandInterface> commands;
    private final Receiver receiver;
    public Help(HashMap<String, CommandInterface> commands, DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Show allowed commands");
        this.commands = commands;
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg){
        if (arg.length() > 0){
            System.out.println("This command doesn't require argument. Please, try again.");
            return;
        }else {
            receiver.help(commands);
        }
    }
}
