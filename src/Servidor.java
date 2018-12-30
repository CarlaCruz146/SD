import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;


public class Servidor {
    private String nome;
    private double preco;
    private String tipo;
    private int estado; //0 -> disponivel, 1 -> reservado a pedido, 2 -> reservado a leilao
    private Map<String,Double> propostas; //utilizador, valor

    public Servidor(String nome, double preco, String tipo, int estado){
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.estado = estado;
        this.propostas = new HashMap<>();
    }

    public String getNome(){
        return this.nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getTipo() {
        return tipo;
    }

    public int getEstado() {
        return estado;
    }

    public Map<String,Double>  getPropostas() {
        return this.propostas.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setPropostas(Map<String, Double> propostas) {
        this.propostas = propostas.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
    }

    public void addProposta(String email, double valor) {
        this.propostas.put(email, valor);
    }
}
