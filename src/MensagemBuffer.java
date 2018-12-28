import java.util.ArrayList;

public class MensagemBuffer {
    private ArrayList<String> mensagens;
    private int index;

    public MensagemBuffer() {
        mensagens = new ArrayList<>();
        index = 0;
    }

    synchronized public void write(String message) {
        mensagens.add(message);
        notifyAll();
    }

    synchronized public String read() throws InterruptedException {
        while(isEmpty())
            wait();

        String message = mensagens.get(index);
        index += 1;

        return message;
    }

    synchronized public void reset() {
        index = 0;
    }

    synchronized public void acknowledge(int amount) {
        for (int i = 0; i < amount; i++)
            mensagens.remove(0);

        index = 0;
    }

    synchronized public boolean isEmpty() {
        return mensagens.size() == index;
    }
}
