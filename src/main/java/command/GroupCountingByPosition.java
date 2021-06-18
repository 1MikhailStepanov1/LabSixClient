package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class GroupCountingByPosition extends CommandAbstract {
    private final Receiver receiver;

    public GroupCountingByPosition(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Group elements by field \"Position\" and show amount of elements in each group");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }

    @Override
    public void exe(String arg){
        if (arg.length() > 0){
            System.out.println("This command doesn't require argument. Please, try again.");
            return;
        }else {
            receiver.groupCountingByPosition();
        }
    }
}
