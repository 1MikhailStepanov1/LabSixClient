package client;

import data.Worker;
import util.SerializeData;
import util.WorkerToUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

public class ReceiverData extends Thread{
    private final DatagramChannel datagramChannel;
    private final WorkerToUser workerToUser;

    public ReceiverData(DatagramChannel datagramChannel, WorkerToUser workerToUser){
        this.datagramChannel = datagramChannel;
        this.workerToUser = workerToUser;
    }

    public void run(){
        Object answer = null;
        LinkedList<SerializeData> answerList;
        while (!isInterrupted() && datagramChannel.isOpen()){
            ByteBuffer buffer = ByteBuffer.allocate(1024*4);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            try {
                if (datagramChannel.receive(buffer) != null){
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    answer = objectInputStream.readObject();
                }else continue;
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
                    for (Object object : answerList){
                        workerToUser.WorkerToConsole((Worker) ((SerializeData)object).getData());
                    }
                    continue;
                }
            }
            answer = null;
            buffer.clear();
        }
    }
    public void stopReceive() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }
}
