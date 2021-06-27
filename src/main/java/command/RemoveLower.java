package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class RemoveLower extends CommandAbstract {
    private final Receiver receiver;

    public RemoveLower(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        super("Delete all elements from collection which are smaller than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException {
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            receiver.removeLower();
        }
    }
}
