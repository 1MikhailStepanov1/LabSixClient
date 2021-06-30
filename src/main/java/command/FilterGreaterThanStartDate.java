package command;

import exceptions.IncorrectArgumentException;
import utility.Console;
import utility.Invoker;
import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FilterGreaterThanStartDate extends CommandAbstract {
    private final Receiver receiver;

    public FilterGreaterThanStartDate(DatagramChannel datagramChannel, SocketAddress socketAddress, Console console, Invoker invoker) {
        super("Show elements with value of field \"StartDate\", which is bigger than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress, console, invoker);
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
                throw new IncorrectArgumentException("Incorrect argument. Follow format dd.mm.yyyy hh:mm:ss +/-hh:mm");
            }
            receiver.filterGreaterThanStartDate(arg);
        }
    }
}
