import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Classe da thread ClienteReader.
 * @author Grupo 24
 */
public class ClienteReader implements Runnable{
     /** BufferedReader */
    private BufferedReader in;
    /** Socket */
    private Socket socket;
    /** Menu do cliente */
    private Menu menu;

    /**
     * Construtor da classe ClienteReader com parâmetros.
     * @param menu Menu de opções
     * @param socket Socket
     * @throws IOException
     */
    public ClienteReader(Menu menu, Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.menu = menu;
    }

    /**
     * Método para executar a thread ClienteReader.
     */
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

    /**
     * Método para fazer o parser do comando recebido.
     *@param comando Comando recebido
     */
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
            case "LEILAOEMCURSO":
                System.out.println(comando);
                menu.setOp(1);
                menu.showMenu();
                break;
            case "PROPOSTALEILAO":
                menu.setOp(4);
                menu.showMenu();
                break;
            default:
                System.out.println(comando);
                menu.showMenu();
        }
    }
}
