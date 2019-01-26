package Servidor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Classe da thread ServidorWriter.
 * @author Grupo 24
 */
public class ServidorWriter implements Runnable{
    private MensagemBuffer msg;
    private BufferedWriter out;

    /**
     * Construtor da classe ServidorWriter com parâmetros.
     * @param msg Buffer de mensagens
     * @param cl Socket
     * @throws IOException
     */
    ServidorWriter(MensagemBuffer msg, Socket cl) throws IOException {
        this.msg = msg;
        this.out = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
    }

    /**
     * Método para executar a thread ServidorWriter.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String r = msg.read();
                out.write(r);
                out.newLine();
                out.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
