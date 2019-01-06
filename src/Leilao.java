import java.util.ArrayList;
import java.util.List;

public class Leilao {
    private Lance bestLance;
    private int id;
    List<Utilizador> compradores;
    private String tipo;

    /**
     * Construtor da classe Leilão com paramêtros.
     * @param id Id do leilão.
     * @param tipo Tipo do Leilão.
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
     * Devolve o tipo do leilão.
     * @return tipo
     */
    public String getTipo(){
        return this.tipo;
    }

    /**
     * Faz uma licitação no leilão.
     */
    synchronized public void proposta(Utilizador u, double proposta) throws LicitacaoInvalidaException {
        if (bestLance.getValor() > proposta)
            throw new LicitacaoInvalidaException("Já existe uma licitação com um valor superior");

        compradores.add(u);
        bestLance = new Lance(u, proposta);
    }

    /**
     * Termina o leilão, indicando o vencedor.
     */
    synchronized public Lance terminaLeilao(){
        String vencedor = bestLance.getComprador().getEmail();
        for(Utilizador u: this.compradores)
            u.notificar("Leilão encerrado! Vencedor: " + vencedor);
        return bestLance;
    }
}
