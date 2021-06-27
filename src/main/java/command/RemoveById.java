package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class RemoveById extends CommandAbstract {
    private final Receiver receiver;

    public RemoveById(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        super("Delete element with indicated id");
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException{
        if (arg.length() == 0){
            throw new IncorrectArgumentException("Command needs argument");
        }else {
            try {
                Long tempId = Long.parseLong(arg);
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
                throw new IncorrectArgumentException("Incorrect argument.");
            }
            receiver.removeById(arg);
        }
    }
}
