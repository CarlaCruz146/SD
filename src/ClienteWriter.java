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
                if(op == 1)
                    pedirServidor();
                if(op == 2){
                    cancelarServidor();
                }
                if(op == 3)
                    mostraReservasA();
                if(op == 4)
                    consultarDivida();
                if(op == 5)
                    reservarLeilao();
                if(op == 0)
                    terminarSessao();
               break;
            case 2:
                if (op == 1)
                    pedirPequeno();
                if(op == 2)
                    pedirGrande();
                if(op == 0)
                    cancelarPedido();
                break;
            case 3:
                if (op == 1)
                    pedirPequenoLeilao();
                if(op == 2)
                    pedirGrandeLeilao();
                if(op == 0)
                    cancelarPedido();
                break;
            case 4:
                if (op == 1)
                    inserirProposta();
                if(op == 0)
                    cancelarPedido();
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

    private void terminarSessao() throws IOException {
        out.write("TERMINARSESSAO");
        out.newLine();
        out.flush();
    }

    private void pedirServidor() throws IOException {
        out.write("PEDIR");
        out.newLine();
        out.flush();
    }

    private void cancelarPedido() throws IOException {
        out.write("CANCELARPEDIDO");
        out.newLine();
        out.flush();
    }

    private void pedirPequeno() throws IOException {
        out.write("PEDIRPEQUENO");
        out.newLine();
        out.flush();
    }

    private void pedirGrande() throws IOException {
        out.write("PEDIRGRANDE");
        out.newLine();
        out.flush();
    }

    private void cancelarServidor() throws IOException{
        String id = menu.readString("Identificador: ");
        String q = String.join(" ", "CANCELARSERVIDOR", id);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void consultarDivida() throws IOException{
        out.write("DIVIDA");
        out.newLine();
        out.flush();
    }
    
    private void mostraReservasA() throws IOException{
        out.write("RESERVAS");
        out.newLine();
        out.flush();
    }

    private void reservarLeilao() throws IOException{
        out.write("LEILAO");
        out.newLine();
        out.flush();
    }

    private void pedirPequenoLeilao() throws IOException {
        String valor = menu.readString("Valor: ");
        String q = String.join(" ", "PEDIRPEQUENOLEILAO", valor);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void pedirGrandeLeilao() throws IOException {
        String valor = menu.readString("Valor: ");
        String q = String.join(" ", "PEDIRGRANDELEILAO", valor);
        out.write(q);
        out.newLine();
        out.flush();
    }

    private void inserirProposta() throws IOException {
        String valor = menu.readString("Proposta: ");
        String q = String.join(" ", "PROPOSTA", valor);
        out.write(q);
        out.newLine();
        out.flush();
    }
}
