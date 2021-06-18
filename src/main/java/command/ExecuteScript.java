package command;

import utility.Receiver;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class ExecuteScript extends CommandAbstract {
    private final Receiver receiver;
    public ExecuteScript(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        super("Read and execute script from entered file.");
        this.receiver = new Receiver(datagramChannel, socketAddress);
    }
    @Override
    public void exe(String arg){
        if (arg.length() > 0){
            receiver.executeScript(arg);
        } else {
            System.out.println("Input is incorrect. Please, try again.");
        }
    }
}
