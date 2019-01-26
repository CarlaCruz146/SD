package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe main do Servidor.
 * @author Grupo 24
 */
public class ServidorMain {
    /**
     * Método main.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(9999);
        ServerCloud serverCloud = new ServerCloud();
        while(true){
            MensagemBuffer msg = new MensagemBuffer();
            Socket socket = s.accept();
            ServidorReader sr =  new ServidorReader(msg,socket,serverCloud);
            ServidorWriter sw = new ServidorWriter(msg,socket);
            Thread tw = new Thread(sw);
            Thread tr = new Thread(sr);
            tw.start();
            tr.start();
        }
    }
}
