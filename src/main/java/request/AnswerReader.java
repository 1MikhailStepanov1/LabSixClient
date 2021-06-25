package request;

import utility.WorkerToUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class AnswerReader {
    private final WorkerToUser workerToUser = new WorkerToUser();
    private final DatagramChannel datagramChannel;

    public AnswerReader(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public void readAnswer() {
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
        byteBuffer.clear();
    }

    public void stopRead() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }
}
