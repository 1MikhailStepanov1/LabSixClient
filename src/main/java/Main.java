import client.ReceiverData;
import util.Console;
import util.WorkerToUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Console console = new Console(scanner);
        InetAddress serverAddress = null;
        InetAddress clientAddress = null;
        int serverPort = 1234;
        try {
            serverAddress = InetAddress.getLocalHost();
            clientAddress = InetAddress.getLocalHost();
            if (args.length != 0 && args[0].contains(":")) {
                serverAddress = InetAddress.getByName(args[0].split(":")[0]);
                serverPort = Integer.parseInt(args[0].split(":")[1]);
            } else {
                System.out.println("Server IP wasn't found. Default value 'localhost:1234' will be used.");
            }
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect format ");
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            return;
        }
        SocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);
        DatagramChannel datagramChannel;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(clientAddress, 0));
            System.out.println(datagramChannel.getLocalAddress());
            datagramChannel.configureBlocking(false);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        console.activeMode(datagramChannel, serverSocketAddress);
        WorkerToUser workerToUser = new WorkerToUser();
        ReceiverData receiverData = new ReceiverData(datagramChannel, workerToUser);
        try {
            receiverData.stopReceive();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        receiverData.interrupt();
    }
}
