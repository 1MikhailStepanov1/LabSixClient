package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Invoker;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Info extends CommandAbstract {
    private final Receiver receiver;

    public Info(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console, Invoker invoker) {
        super("Show information about collection (type, initialization time and etc.)");
        this.receiver = new Receiver(datagramChannel, socketAddress, console, invoker);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException{
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            receiver.info();
        }
    }
}
