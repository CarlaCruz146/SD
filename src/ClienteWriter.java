import java.io.*;
import java.net.Socket;

public class ClienteWriter implements Runnable{
    private BufferedWriter out;
    private Socket socket;
    private Menu menu;

    public ClienteWriter(Menu menu, Socket socket){
        try{
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.menu = menu;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        int op;
        menu.showMenu();
        try {
            while ((op = menu.op()) != -1) {
                parse(op);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parse(Integer op) throws IOException{
        switch (menu.getOp()) {
            case 0:
                if (op == 1) {
                    iniciarSessao();
                }
                if (op == 2) {
                    registar();
                }
                if(op == 0)
                    System.exit(0);
                break;
            case 1:
                if(op == 0)
                    terminarSessao();
               break;
        }
    }

    private void iniciarSessao() throws IOException{
        String email = menu.readString("Email: ");
        String password = menu.readString("Password: ");
        String q = String.join(" ", "INICIARSESSAO", email, password);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void registar() throws IOException{
        String email = menu.readString("Email: ");
        String password = menu.readString("Password: ");
        String q = String.join(" ", "REGISTAR", email, password);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void terminarSessao() throws IOException{
        out.write("TERMINARSESSAO");
        out.newLine();
        out.flush();

    }
}
