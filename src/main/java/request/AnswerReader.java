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
    private boolean answerAccepted;
    private boolean validationAccepted = false;

    public AnswerReader(DatagramChannel datagramChannel, SocketAddress socketAddress) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
    }

    public void readAnswer() throws ServerIsNotAvailableException {
        long time = System.currentTimeMillis();
        Object answer;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        while (true) {
            if ((System.currentTimeMillis() - time) < 5000) {
                try {
                    datagramChannel.receive(byteBuffer);
                    if (byteBuffer.position() != 0) {
                        ((Buffer) byteBuffer).flip();
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        answer = objectInputStream.readObject();
                    } else continue;
                } catch (IOException | ClassNotFoundException exception) {
                    System.out.println("Object can't be read from buffer.");
                    return;
                }
                SerializationForClient answerChanged = (SerializationForClient) answer;
                if (answerChanged.getStatus()) {
                    System.out.println("Command was done successfully.\n");
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
                setAnswerAccepted(true);
                ((Buffer) byteBuffer).clear();
                break;
            } else {
                setAnswerAccepted(true);
                throw new ServerIsNotAvailableException("Server is not available at the moment.");
            }
        }
    }

    public boolean readValidation() throws ServerIsNotAvailableException {
        long time = System.currentTimeMillis();
        Object answer;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        while (true) {
            if ((System.currentTimeMillis() - time) < 5000) {
                try {
                    datagramChannel.receive(byteBuffer);
                    if (byteBuffer.position() != 0) {
                        ((Buffer) byteBuffer).flip();
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        answer = objectInputStream.readObject();
                    } else continue;
                } catch (IOException | ClassNotFoundException exception) {
                    System.out.println("Object can't be read from buffer.");
                    return false;
                }
                SerializationForClient answerChanged = (SerializationForClient) answer;;
                if (answerChanged.getStatus()) {
                    ((Buffer)byteBuffer).clear();
                    System.out.println("Validation was passed good.");
                    setValidationAccepted(true);
                    return true;
                } else {
                    ((Buffer)byteBuffer).clear();
                    System.out.println("Validation wasn't passed.");
                    setValidationAccepted(true);
                    return false;
                }
            } else {
                setValidationAccepted(true);
                throw new ServerIsNotAvailableException("Server is not available at the moment.");
            }
        }
    }

    public void setAnswerAccepted(boolean answerAccepted) {
        this.answerAccepted = answerAccepted;
    }
    public boolean isAnswerAccepted() {
        return answerAccepted;
    }

    public boolean isValidationAccepted() {
        return validationAccepted;
    }

    public void setValidationAccepted(boolean validationAccepted) {
        this.validationAccepted = validationAccepted;
    }

    public void stopRead() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }


}
