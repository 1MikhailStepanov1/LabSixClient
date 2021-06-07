package client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Sender {
    private final DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;

    public Sender(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;

    }

    public void sendObject(Object serializedObject) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try {
            datagramChannel.connect(socketAddress);
            objectOutputStream.writeObject(serializedObject);
            objectOutputStream.flush();
            int packageSize = byteArrayOutputStream.size();
            while (datagramChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray())) < packageSize) ;
            datagramChannel.disconnect();
            Thread.sleep(60);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } catch (PortUnreachableException | BindException exception) {
            System.out.println("Server is not available at the moment, please, try again later.");
        } catch (IOException exception) {
            System.out.println("Failed to send command.");
            System.out.println(exception.getMessage());
            exception.getStackTrace();
        }
    }
}
