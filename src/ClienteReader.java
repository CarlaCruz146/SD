import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteReader implements Runnable{
    private BufferedReader in;
    private Socket socket;
    private Menu menu;

    public ClienteReader(Menu menu, Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.menu = menu;
    }

    public void run() {
        try {
            String comando;
            while ((comando = in.readLine()) != null) {
                parse(comando);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void parse(String comando){
        String[] p = comando.split(" ",2);
        switch(p[0].toUpperCase()){
            case "AUTENTICADO":
                menu.setOp(1);
                menu.showMenu();
                break;
            case "REGISTADO":
                menu.setOp(0);
                menu.showMenu();
                break;
            case "SESSAOTERMINADA":
                menu.setOp(0);
                menu.showMenu();
                break;
            case "PEDIDO":
                menu.setOp(2);
                menu.showMenu();
                break;
            case "PEDIDOCANCELADO":
                menu.setOp(1);
                menu.showMenu();
                break;
            case "IDENTIFICADOR":
                System.out.println(comando);
                menu.setOp(1);
                menu.showMenu();
                break;
            case "RESERVACANCELADA":
                menu.setOp(1);
                menu.showMenu();
                break;
            case "PEDIDOLEILAO":
                menu.setOp(3);
                menu.showMenu();
                break;
            case "PROPOSTALEILAO":
                menu.setOp(4);
                menu.showMenu();
                break;
            case "PERDELEILAO":
                System.out.println("Perdeu o leilão. Reserva não efetuada");
                menu.setOp(4);
                menu.showMenu();
            default:
                System.out.println(comando);
                menu.showMenu();
        }
    }
}
