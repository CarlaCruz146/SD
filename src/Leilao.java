public class Leilao {
    private String vencedor;
    private double valorVenc;
    private String tipo;

    public Leilao(){
        this.vencedor="";
        this.valorVenc=0;
        this.tipo="";
    }

    public Leilao(String vencedor, int valorVenc, String tipo ){
        this.vencedor = vencedor;
        this.valorVenc = valorVenc;
        this.tipo = tipo;
    }
    public String getVencedor() {
        return this.vencedor;
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

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
