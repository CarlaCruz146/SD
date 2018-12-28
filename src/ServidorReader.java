import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
                 } catch (PedidoInvalidoException | UtilizadorExistenteException e){
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

    private String parse(String r) throws PedidoInvalidoException,UtilizadorExistenteException{
        String[] p = r.split(" ",2);
        switch(p[0].toUpperCase()){
            case "INICIARSESSAO":
                verificaAutenticacao(false);
                return this.iniciarSessao(p[1]);
            case "REGISTAR":
                verificaAutenticacao(false);
                return this.registar(p[1]);
            case"TERMINARSESSAO":
                verificaAutenticacao(true);
                return this.terminarSessao();
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
}