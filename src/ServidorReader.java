import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Classe da thread ServidorReader.
 * @author Grupo 24
 */
public class ServidorReader implements Runnable {
    /** Utilizador autenticado*/
    private Utilizador utilizador;
    /** BufferedReader */
    private BufferedReader in;
    /** Buffer de mensagens */
    private MensagemBuffer msg;
    /** Socket */
    private Socket socket;
    /** ServerCloud */
    private ServerCloud serverCloud;

    /**
     * Construtor da classe ServidorReader com parâmetros.
     * @param msg Buffer de mensagens
     * @param socket Socket
     * @param serverCloud ServerCloud
     * @throws IOException
     */
    public ServidorReader(MensagemBuffer msg, Socket socket, ServerCloud serverCloud) throws IOException {
        this.msg = msg;
        this.socket = socket;
        this.serverCloud = serverCloud;
        this.utilizador = null;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Método para executar a thread ServidorReader.
     */
    public void run() {
        String r;
        while ((r = readLine()) != null) {
            try {
                msg.write(parse(r));
            } catch (IndexOutOfBoundsException e) {
                msg.write("Inadequado");
            } catch (PedidoInvalidoException | UtilizadorExistenteException | ServidorInexistenteException
                    | ReservaInexistenteException | LicitacaoInvalidaException | LeilaoInexistenteException
                    | InterruptedException e) {
                msg.write(e.getMessage());
            }
        }
     //   endConnection();
        if (this.utilizador == null) {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Lê linha do BufferedReader.
     * @return String lida
     */
    private String readLine() {
        String line = null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            System.out.println("Não foi possível ler novas mensagens");
        }

        return line;
    }
    /**
     * Faz parse das strings lidas
     * @return String
     * @throws PedidoInvalidoException
     * @throws UtilizadorExistenteException
     * @throws ServidorInexistenteException
     * @throws ReservaInexistenteException
     * @throws InterruptedException
     * @throws LeilaoInexistenteException
     * @throws LicitacaoInvalidaException
     */
    private String parse(String r) throws PedidoInvalidoException, UtilizadorExistenteException,
            ServidorInexistenteException, ReservaInexistenteException, InterruptedException,
            LeilaoInexistenteException, LicitacaoInvalidaException{
        String[] p = r.split(" ", 2);
        switch (p[0].toUpperCase()) {
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
                return this.apresentaReservas().toString();
            case "DIVIDA":
                return this.verDivida();
            case "LEILAO":
                return "PEDIDOLEILAO";
            case "PEDIRPEQUENOLEILAO":
                return reservarLeilao(p[1],"Pequeno");
            case "PEDIRGRANDELEILAO":
                return reservarLeilao(p[1],"Grande");
            case "CATALOGO":
                return this.apresentaCatalogo().toString();
            default:
                return "ERRO";
        }
    }

    /**
     * Inicia sessão.
     * @param in Linha lida do BufferedReader
     * @return String
     * @throws PedidoInvalidoException
     */
    private String iniciarSessao(String in) throws PedidoInvalidoException {
        String[] p = in.split(" ");
        if (p.length != 2)
            throw new PedidoInvalidoException("Dados incorretos");
        this.utilizador = serverCloud.iniciarSessao(p[0], p[1],msg);
        return "AUTENTICADO";
    }

    /**
     * Regista um utilizador.
     * @param in Linha lida do BufferedReader
     * @return String
     * @throws PedidoInvalidoException
     * @throws UtilizadorExistenteException
     */
    private String registar(String in) throws PedidoInvalidoException, UtilizadorExistenteException {
        String[] p = in.split(" ");
        if (p.length != 2)
            throw new PedidoInvalidoException("Dados incorretos");
        serverCloud.registar(p[0], p[1]);
        return "REGISTADO";
    }

    /**
     * Verifica a autenticação de um utilizador.
     * @param estado Estado da sessão
     * @throws PedidoInvalidoException
     */
    private void verificaAutenticacao(Boolean estado) throws PedidoInvalidoException {
        if (estado && utilizador == null)
            throw new PedidoInvalidoException("Acesso negado");
        if (!estado && utilizador != null)
            throw new PedidoInvalidoException("Já existe um utilizador autenticado");
    }

    /**
     * Termina sessão.
     * @return String
     * @throws PedidoInvalidoException
     */
    private String terminarSessao() {
        this.utilizador = null;
        return "SESSAOTERMINADA";
    }

    /**
     * Reserva um servidor a pedido.
     * @param tipo Tipo de servidor
     * @return String
     * @throws ServidorInexistenteException
     */
    private String pedirServidor(String tipo) throws ServidorInexistenteException {
        int id = serverCloud.pedirServidor(this.utilizador, tipo);
        return String.join(" ", "IDENTIFICADOR", Integer.toString(id));
    }

    /**
     * Cancela a reserva de um servidor.
     * @param in Linha lida do BufferedReader
     * @return String
     * @throws ReservaInexistenteException
     * @throws LeilaoInexistenteException
     */
    private String cancelarServidor(String in) throws ReservaInexistenteException, LeilaoInexistenteException {
        int id = Integer.parseInt(in);
        serverCloud.cancelarServidor(id, this.utilizador);
        return "RESERVACANCELADA";
    }

    /**
     * Devolve uma lista com informação das reservas ativas do utilizador autenticado.
     * @return List
     */
    private List<String> apresentaReservas() {
        List<Reserva> rs = serverCloud.reservasAtivas(this.utilizador);
        List<String> resultado = new ArrayList<>();
        for (Reserva re : rs) {
            int id = re.getId();
            String nomeS = re.getNome();
            String r = String.join(" ", "Reserva com ID", Integer.toString(id), "do Servidor", nomeS);
            resultado.add(r);
        }
        if(resultado.size() == 0) resultado.add("Não tem reservas ativas.");
        return resultado;
    }

    /**
     * Apresenta a dívida atual do utilizador.
     * @return String
     */
    private String verDivida() {
        double val = serverCloud.dividaAtual(this.utilizador);
        return String.join(" ", "DIVIDA:", Double.toString(val));
    }

    /**
     * Reserva um servidor a leilão.
     * @param in Linha lida do BufferedReader
     * @param tipo Tipo de servidor
     * @return String
     * @throws InterruptedException
     * @throws LicitacaoInvalidaException
     * @throws LeilaoInexistenteException
     */
    private String reservarLeilao(String in, String tipo) throws InterruptedException,LicitacaoInvalidaException,LeilaoInexistenteException {
        double valor = Double.parseDouble(in);
        int idR = serverCloud.reservarLeilao(valor,tipo,this.utilizador);
        if(idR!=-1) return String.join(" ", "IDENTIFICADOR", Integer.toString(idR));
        return "LEILAOEMCURSO";
    }

    /**
     * Devolve uma lista com informação dos servidores disponíveis.
     * @return List
     */
    private List<String> apresentaCatalogo() {
        List<Servidor> rs = serverCloud.getServidoresAtivos();
        List<String> resultado = new ArrayList<>();
        for (Servidor s : rs) {
            double preco = s.getPreco();
            String nome = s.getNome();
            String tipo = s.getTipo();
            String r = String.join(" ","Tipo->", tipo , "Servidor->", nome, "Preço:", Double.toString(preco) );
            resultado.add(r);
        }
        if(resultado.size() == 0) resultado.add("Não tem servidores ativos.");
        return resultado;
    }
}

