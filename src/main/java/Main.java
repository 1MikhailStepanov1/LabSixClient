import request.AnswerReader;
import utility.CommandReader;
import utility.Console;
import utility.Invoker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Console console = new Console(scanner);
        InetAddress serverAddress = null;
        InetAddress clientAddress = null;
        int port = 9898;
        try {
            serverAddress = InetAddress.getLocalHost();
            clientAddress = InetAddress.getLocalHost();
            if (args.length != 0 && args[0].contains(":")){
                serverAddress = InetAddress.getByName(args[0].split(":")[0]);
                port = Integer.parseInt(args[0].split(":")[1]);
            }else {
                System.out.println("Server IP wasn't found. Default value localhost:9898 will be used.");
            }
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            return;
        } catch (NumberFormatException exception){
            System.out.println("Incorrect format of port.");
        }
        SocketAddress socketAddress = new InetSocketAddress(serverAddress, port);
        DatagramChannel datagramChannel;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(clientAddress, 0));
            System.out.println(datagramChannel.getLocalAddress());
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }
        Invoker invoker = new Invoker();
        invoker.initMap(datagramChannel, socketAddress);
        CommandReader commandReader = new CommandReader(console, invoker);
        commandReader.activeMode();
        AnswerReader answerReader = new AnswerReader(datagramChannel);
        answerReader.readAnswer();
        try {
            AnswerReader.stopRead();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
