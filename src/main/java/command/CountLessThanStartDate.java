package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CountLessThanStartDate extends CommandAbstract {
    private final Receiver receiver;

    public CountLessThanStartDate(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console) {
        super("Show amount of elements with field \"StartDate\" which is lower than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress, console);
    }

    @Override
    public void exe(String arg) throws IncorrectArgumentException {
        if (arg.length() == 0){
            throw new IncorrectArgumentException("Command needs argument");
        }else {
            ZonedDateTime tempTime = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu H:mm:ss z");
            try {
                tempTime = ZonedDateTime.parse(arg, formatter);
            } catch (DateTimeParseException exception) {
                System.out.println(exception.getMessage());
                throw new IncorrectArgumentException("Incorrect argument.");
            }
            receiver.countLessThanStartDate(arg);
        }
    }
}
