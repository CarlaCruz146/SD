public class Lance {
    private Utilizador comprador;
    private double valorVenc;

    /**
     * Construtor da classe Lance com paramêtros.
     * @param comprador Comprador que faz o lance.
     * @param valorVenc Valor do lance.

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
     * Devolve o valor do lande.
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
     * @param vencedor Vencedor do lance
     */
    public void setComprador(String vencedor) {
        this.comprador = comprador;
    }

}
