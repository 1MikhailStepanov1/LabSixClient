package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class RemoveGreater extends CommandAbstract {
    private final Receiver receiver;

    public RemoveGreater(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Delete all elements from collection which are bigger than indicated one");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg){
        if (arg.length() > 0){
            System.out.println("This command doesn't require argument. Please, try again.");
            return;
        }else {
            receiver.removeGreater();
        }
    }
}
