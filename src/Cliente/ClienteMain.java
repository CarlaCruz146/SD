package Cliente;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe main do Cliente.
 * @author Grupo 24
 */
public class ClienteMain {
    /**
     * Método main.
     * @throws IOException
     */
    public static void main(String[] args) {
        Socket socket = null;
        try {

            socket = new Socket("127.0.0.1", 9999);
            Menu menu =  new Menu();
            ClienteWriter clw = new ClienteWriter(menu, socket);
            ClienteReader clr = new ClienteReader(menu,socket);
            Thread reader = new Thread(clr);
            Thread writer = new Thread(clw);
            reader.start();
            writer.start();
        } catch(IOException e){System.out.println(e.getMessage());}
    }
}
