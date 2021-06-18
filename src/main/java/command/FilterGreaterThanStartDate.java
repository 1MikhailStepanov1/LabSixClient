package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class FilterGreaterThanStartDate extends CommandAbstract {
    private final Receiver receiver;

    public FilterGreaterThanStartDate(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Show elements with value of field \"StartDate\", which is bigger than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg) {
        receiver.filterGreaterThanStartDate(arg);
    }
}
