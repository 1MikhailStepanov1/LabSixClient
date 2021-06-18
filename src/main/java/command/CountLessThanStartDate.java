package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class CountLessThanStartDate extends CommandAbstract {
    private final Receiver receiver;

    public CountLessThanStartDate(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Show amount of elements with field \"StartDate\" which is lower than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg) {
        receiver.countLessThanStartDate(arg);
    }
}
