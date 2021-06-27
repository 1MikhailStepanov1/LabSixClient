package command;

import exceptions.IncorrectArgumentException;
import exceptions.ValidationException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Update extends CommandAbstract {
    private final Receiver receiver;

    public Update(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        super("Update element with indicated id");
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException, ValidationException {
        if (arg.length() == 0) {
            throw new IncorrectArgumentException("Command doesn't need argument");
        } else {
            try {
                Integer tempInt = Integer.parseInt(arg);
            } catch (NumberFormatException exception){
                throw new IncorrectArgumentException("Incorrect format of id.");
            }
            receiver.update(arg);
        }
    }
}
