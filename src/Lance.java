public class Lance {
    private Utilizador comprador;
    private double valorVenc;
    private String tipo;

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

    public String getTipo() {
        return this.tipo;
    }

    public void setValor(double valorVenc) {
        this.valorVenc = valorVenc;
    }

    public void setComprador(String vencedor) {
        this.comprador = comprador;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
