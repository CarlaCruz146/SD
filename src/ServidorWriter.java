import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServidorWriter implements Runnable{
    private MensagemBuffer msg;
    private BufferedWriter out;

    ServidorWriter(MensagemBuffer msg, Socket cl) throws IOException {
        this.msg = msg;
        this.out = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String r = msg.read();
                //if(r.equals("SESSAOTERMINADA")) break;
                out.write(r);
                out.newLine();
                out.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
