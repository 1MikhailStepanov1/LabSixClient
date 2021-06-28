package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Invoker;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Clear extends CommandAbstract {
    private final Receiver receiver;

    public Clear(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console, Invoker invoker) {
        super("Clear collection");
        this.receiver = new Receiver(datagramChannel, socketAddress, console, invoker);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException{
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            receiver.clear();
        }
    }
}
