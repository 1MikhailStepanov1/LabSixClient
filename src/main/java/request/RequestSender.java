package request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {
    private final DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;

    public RequestSender(DatagramChannel datagramChannel, SocketAddress socketAddress){
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
    }

    public void sendRequest(Object serializeCommand){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            datagramChannel.connect(socketAddress);
            objectOutputStream.writeObject(serializeCommand);
            objectOutputStream.flush();
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            datagramChannel.send(buffer, socketAddress);
            datagramChannel.disconnect();
        } catch (PortUnreachableException exception){
            System.out.println("Server is not available at the moment. Please, try again later.");
        } catch (IOException exception) {
            System.out.println("Client doesn't connect to server. Please, reload program and try again.");
        }

    }
}
