package request;

import data.Worker;
import utility.WorkerToUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

public class AnswerReader {
    private final WorkerToUser workerToUser = new WorkerToUser();
    private static DatagramChannel datagramChannel;

    public AnswerReader(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public void readAnswer() {
        Object answer = null;
        LinkedList<Serialization> answerList;
        while (datagramChannel.isOpen()){
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            try {
                if (datagramChannel.receive(byteBuffer) != null){
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    answer = objectInputStream.readObject();
                } else continue;
            } catch (IOException | ClassNotFoundException exception) {
                continue;
            }
            if (answer instanceof LinkedList){
                answerList = (LinkedList) answer;
                if (answerList.peek() == null){
                    continue;
                }
                if (answerList.peek().getData() instanceof String){
                    while (!answerList.isEmpty()){
                        System.out.println((String) answerList.pollFirst().getData());
                    }
                    continue;
                }
                if (answerList.peek().getData() instanceof Worker){
                    answerList.forEach((object)->workerToUser.workerToConsole((Worker) ((Serialization)object).getData()));
                    continue;
                }
            }
            answer = null;
            byteBuffer.clear();
        }
    }

    public static void stopRead() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }
}
