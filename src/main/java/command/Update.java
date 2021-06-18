package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Update extends CommandAbstract {
    private final Receiver receiver;

    public Update(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Update element with indicated id");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg) {
        receiver.update(arg);
    }
}
