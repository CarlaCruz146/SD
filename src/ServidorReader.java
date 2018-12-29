import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServidorReader implements Runnable {
    private Utilizador utilizador;
    private BufferedReader in;
    private MensagemBuffer msg;
    private Socket socket;
    private ServerCloud serverCloud;
    //private Cliente c;

    public ServidorReader(MensagemBuffer msg,Socket socket) throws IOException {
        this.msg = msg;
        this.socket = socket;
        this.serverCloud = new ServerCloud();
        this.utilizador = null;
        in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run(){
            String r;
            while ((r = readLine()) != null) {
                try {
                    msg.write(parse(r));
                }
                catch(IndexOutOfBoundsException e){
                    msg.write("Inadequado");
                 } catch (PedidoInvalidoException | UtilizadorExistenteException | ServidorInexistenteException | ReservaInexistenteException e){
                    msg.write(e.getMessage());
                }
            }
            if(this.utilizador == null){
                try{
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();
                }catch (IOException e){e.printStackTrace();}
            }
    }

    private String readLine() {
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            System.out.println("Não foi possível ler novas mensagens");
        }

        return line;
    }

    private String parse(String r) throws PedidoInvalidoException,UtilizadorExistenteException,ServidorInexistenteException,ReservaInexistenteException{
        String[] p = r.split(" ",2);
        switch(p[0].toUpperCase()){
            case "INICIARSESSAO":
                verificaAutenticacao(false);
                return this.iniciarSessao(p[1]);
            case "REGISTAR":
                verificaAutenticacao(false);
                return this.registar(p[1]);
            case "TERMINARSESSAO":
                verificaAutenticacao(true);
                return this.terminarSessao();
            case "PEDIR":
                return "PEDIDO";
            case "PEDIRPEQUENO":
                return this.pedirServidor("Pequeno");
            case "PEDIRGRANDE":
                return this.pedirServidor("Grande");
            case "CANCELARPEDIDO":
                return "PEDIDOCANCELADO";
            case "CANCELARSERVIDOR":
                return this.cancelarServidor(p[1]);
            case "RESERVAS":
                return this.apresentaReservas();
            case "DIVIDA":
                return this.verDivida();
            case "LEILAO":
                return "PEDIDOLEILAO";
            case "PEDIRPEQUENOLEILAO":
                return "PROPOSTALEILAO";
            case "PEDIRGRANDELEILAO":
                return "PROPOSTALEILAO";
            case "PROPOSTA":
                return this.reservarLeilao();
            default: return "ERRO";
        }
    }

    private String iniciarSessao(String in) throws PedidoInvalidoException{
        String[] p = in.split(" ");
        if(p.length!=2)
            throw new PedidoInvalidoException("Dados incorretos");
        this.utilizador = serverCloud.iniciarSessao(p[0], p[1]);
        return "AUTENTICADO";
    }

    private String registar(String in) throws PedidoInvalidoException, UtilizadorExistenteException{
        String[] p = in.split(" ");
        if (p.length != 2)
            throw new PedidoInvalidoException("Dados incorretos");
        serverCloud.registar(p[0], p[1]);
        return "REGISTADO";
    }

    private void verificaAutenticacao(Boolean estado) throws PedidoInvalidoException{
        if(estado && utilizador ==  null)
            throw new PedidoInvalidoException("Acesso negado");
        if(!estado && utilizador!=null)
            throw new PedidoInvalidoException("Já existe um utilizador autenticado");
    }

    private String terminarSessao(){
        this.utilizador = null;
        return "SESSAOTERMINADA";
    }

    private String pedirServidor(String tipo) throws ServidorInexistenteException{
        int id = serverCloud.pedirServidor(this.utilizador, tipo);
        return  String.join(" ", "IDENTIFICADOR", Integer.toString(id));
    }

    private String cancelarServidor(String in) throws ReservaInexistenteException{
        int id = Integer.parseInt(in);
        serverCloud.cancelarServidor(id, this.utilizador);
        return "RESERVACANCELADA";
    }

    private String apresentaReservas(){
        List<Reserva> rs = serverCloud.reservasAtivas(this.utilizador);
        String resultado = "";
        for(Reserva re : rs){
            int id = re.getId();
            String nomeS = re.getNome();
            String r = String.join(" ", "Reserva com ID", Integer.toString(id), "do Servidor", nomeS);
            resultado = String.join(" RESERVA-> ", resultado, r);
        }
        return resultado;
    }

    private String verDivida() {
        double val = serverCloud.dividaAtual(this.utilizador);
        return String.join(" ", "DIVIDA:", Double.toString(val));
    }

    private String reservarLeilao(){
        return "";
    }
}