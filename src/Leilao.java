import java.util.ArrayList;
import java.util.List;

public class Leilao {
    private Lance bestLance;
    private int id;
    List<Utilizador> compradores;
    private String tipo;

    public Leilao(int id, String tipo) {
        this.bestLance = new Lance(null,0);
        this.id = id;
        this.compradores = new ArrayList<>();
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    public String getTipo(){
        return this.tipo;
    }

    synchronized public void proposta(Utilizador u, double proposta) throws LicitacaoInvalidaException {
        if (bestLance.getValor() > proposta)
            throw new LicitacaoInvalidaException("Já existe uma licitação com um valor superior");

        compradores.add(u);
        bestLance = new Lance(u, proposta);
    }

    synchronized public Lance terminaLeilao(){
        return bestLance;
    }
}
