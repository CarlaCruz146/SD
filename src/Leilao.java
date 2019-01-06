import java.util.ArrayList;
import java.util.List;

/**
 * Classe dos leilões.
 * @author Grupo 24
 */
public class Leilao {
     /** Melhor lance do leilão */
    private Lance bestLance;
    /** Id do leilão*/
    private int id;
    /** Utilizadores envolvidos no leilão */
    List<Utilizador> compradores;
    /** Tipo do servidor a leilão */
    private String tipo;

    /**
     * Construtor da classe Leilão com paramêtros.
     * @param id Id do leilão.
     * @param tipo Tipo do servidor a leilão.
     */
    public Leilao(int id, String tipo) {
        this.bestLance = new Lance(null,0);
        this.id = id;
        this.compradores = new ArrayList<>();
        this.tipo = tipo;
    }

    /**
     * Devolve o id do leilão.
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Devolve o tipo do servidor a leilão.
     * @return tipo
     */
    public String getTipo(){
        return this.tipo;
    }

     /**
     * Faz uma licitação no leilão.
     * @param u Utilizador que efetua proposta
     * @param proposta valor da proposta
     * @throws LicitacaoInvalidaException
     */
    synchronized public void proposta(Utilizador u, double proposta) throws LicitacaoInvalidaException {
        if (bestLance.getValor() > proposta)
            throw new LicitacaoInvalidaException("Já existe uma licitação com um valor superior");

        compradores.add(u);
        bestLance = new Lance(u, proposta);
    }

    /**
     * Termina o leilão, indicando o vencedor.
     *@return lance vencedor
     */
    synchronized public Lance terminaLeilao(){
        String vencedor = bestLance.getComprador().getEmail();
        for(Utilizador u: this.compradores)
            u.notificar("Leilão encerrado! Vencedor: " + vencedor);
        return bestLance;
    }
}
