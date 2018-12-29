import java.io.IOException;
import java.net.Socket;

public class ClienteMain {
    public static void main(String[] args) {
        Socket socket = null;
        try {

            socket = new Socket("127.0.0.1", 3322);
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