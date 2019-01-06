public class Lance {
    private Utilizador comprador;
    private double valorVenc;

    public Lance(Utilizador comprador, double valorVenc){
        this.comprador = comprador;
        this.valorVenc = valorVenc;
    }
    public Utilizador getComprador() {
        return this.comprador;
    }

    public double getValor() {
        return this.valorVenc;
    }

    public void setValor(double valorVenc) {
        this.valorVenc = valorVenc;
    }

    public void setComprador(String vencedor) {
        this.comprador = comprador;
    }

}
