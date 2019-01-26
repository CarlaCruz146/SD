package Servidor;

import java.util.ArrayList;

/**
 * Classe dos buffers de mensagens.
 * @author Grupo 24
 */
public class MensagemBuffer {
    /** Mensagens guardadas no buffer*/
    private ArrayList<String> mensagens;
    /** Indíce atual do buffer */
    private int index;

    /**
     * Construtor da classe MensagemBuffer sem parâmetros.
     */
    public MensagemBuffer() {
        mensagens = new ArrayList<>();
        index = 0;
    }

    /**
     * Escreve uma mensagem no buffer.
     * @param message Mensagem a adicionar
     */
    synchronized public void write(String message) {
        mensagens.add(message);
        notifyAll();
    }

    /**
     * Lê a última mensagem escrita no buffer.
     * @return mensagem
     * @throws InterruptedException
     */
    synchronized public String read() throws InterruptedException {
        while(isEmpty())
            wait();

        String message = mensagens.get(index);
        index += 1;

        return message;
    }

    /**
     * Verifica se o buffer está vazio
     * @return se está vazio ou não
     */
    synchronized public boolean isEmpty() {
        return mensagens.size() == index;
    }
}
