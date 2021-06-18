package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class RemoveById extends CommandAbstract {
    private final Receiver receiver;

    public RemoveById(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Delete element with indicated id");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg) {
        receiver.removeById(arg);
    }
}
