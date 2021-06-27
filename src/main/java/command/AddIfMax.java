package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class AddIfMax extends CommandAbstract{
    private final Receiver receiver;
    public AddIfMax(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console){
        super("Add new element to the collection, if new element`s value is bigger than element`s maximum in collection");
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException{
        if (arg.length() > 0){
            throw new IncorrectArgumentException("Command doesn't need argument");
        }else {
            receiver.addIfMax();
        }
    }
}
