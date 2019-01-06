
/**
 * Classe dos lances de leilão.
 * @author Grupo 24
 */
public class Lance {
    /** Utilizador que fez o lance */
    private Utilizador comprador;
    /** Valor do lance efetuado */
    private double valorVenc;

    /**
     * Construtor da classe Lance com paramêtros.
     * @param comprador Comprador que faz o lance
     * @param valorVenc Valor do lance
     */
    public Lance(Utilizador comprador, double valorVenc){
        this.comprador = comprador;
        this.valorVenc = valorVenc;
    }

    /**
     * Devolve o comprador que fez o lance.
     * @return Utilizador
     */
    public Utilizador getComprador() {
        return this.comprador;
    }

    /**
     * Devolve o valor do lance.
     * @return double
     */
    public double getValor() {
        return this.valorVenc;
    }

    /**
     * Altera o valor do lance.
     * @param valorVenc Valor do lance
     */
    public void setValor(double valorVenc) {
        this.valorVenc = valorVenc;
    }

    /**
     * Altera o comprador que fez o lance.
     * @param comprador Vencedor do lance
     */
    public void setComprador(Utilizador comprador) {
        this.comprador = comprador;
    }

}
