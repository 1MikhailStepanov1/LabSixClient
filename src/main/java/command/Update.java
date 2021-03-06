package command;

import exceptions.IncorrectArgumentException;
import exceptions.ValidationException;
import utility.Console;
import utility.Invoker;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Update extends CommandAbstract {
    private final Receiver receiver;

    public Update(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console, Invoker invoker) {
        super("Update element with indicated id");
        this.receiver = new Receiver(datagramChannel, socketAddress, console, invoker);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException, ValidationException {
        if (arg.length() == 0) {
            throw new IncorrectArgumentException("Command doesn't need argument");
        } else {
            try {
                Integer tempInt = Integer.parseInt(arg);
                if (tempInt <= 0){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException exception){
                throw new IncorrectArgumentException("Incorrect format of id. (Should be more than 0.)");
            }
            receiver.update(arg);
        }
    }
}
