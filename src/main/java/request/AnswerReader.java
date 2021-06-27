package request;

import exceptions.ServerIsNotAvailableException;
import utility.WorkerToUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class AnswerReader {
    private final WorkerToUser workerToUser = new WorkerToUser();
    private final DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;

    public AnswerReader(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
    }

    public void readAnswer() throws ServerIsNotAvailableException {
        long time = System.currentTimeMillis() - 1;
        System.out.println(time);
        while (true) {
            System.out.println(System.currentTimeMillis());
            if ((time < System.currentTimeMillis() && (System.currentTimeMillis() - time) < 100)) {
                Object answer = new Object();
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
                try {
//                    if (datagramChannel.receive(byteBuffer) != null) {
                    datagramChannel.receive(byteBuffer);
                    ((Buffer)byteBuffer).flip();
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    answer = objectInputStream.readObject();
//                    } else throw new ServerIsNotAvailableException("Server is not available at the moment.");
                } catch (IOException | ClassNotFoundException exception) {
                    System.out.println("Object can't be read from buffer.");
                    exception.printStackTrace();
                }
                SerializationForClient answerChanged = (SerializationForClient) answer;
                if (answerChanged.getStatus()) {
                    System.out.println("Command was done successfully.");
                    if (!answerChanged.getMessage().equals("")) {
                        System.out.println(answerChanged.getMessage());
                    }
                    if (answerChanged.getCount() != null) {
                        System.out.println(answerChanged.getCount());
                    }
                    if (answerChanged.getWorkers() != null) {
                        answerChanged.getWorkers().forEach(workerToUser::workerToConsole);
                    }
                } else {
                    System.out.println("Command wasn't done. Something went wrong.");
                    if (!answerChanged.getMessage().equals("")) {
                        System.out.println(answerChanged.getMessage());
                    }
                }
                ((Buffer)byteBuffer).clear();
                break;
            } else {
                throw new ServerIsNotAvailableException("Server is not available at the moment.");
            }
        }
    }

    public boolean readValidation() throws ServerIsNotAvailableException {
        long time = System.currentTimeMillis();
        System.out.println(time);
        while (true) {
            if (time < System.currentTimeMillis() && (System.currentTimeMillis() - time) < 50000) {
                Object answer = new Object();
                ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
                try {
                    if (datagramChannel.receive(byteBuffer) != null) {
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        answer = objectInputStream.readObject();
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    System.out.println("Object can't be read from buffer.");
                    exception.printStackTrace();
                }
                SerializationForClient answerChanged = null;
                answerChanged = (SerializationForClient) answer;
                if (answerChanged.getStatus()) {
                    byteBuffer.clear();
                    System.out.println("Validation was passed good.");
                    return true;
                } else {
                    byteBuffer.clear();
                    System.out.println("Validation wasn't passed.");
                    return false;
                }
            } else {
                throw new ServerIsNotAvailableException("Server is not available at the moment.");
            }
        }
    }

    public void stopRead() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }
}
