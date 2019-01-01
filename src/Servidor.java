public class Servidor {
    private String nome;
    private double preco;
    private String tipo;
    private int estado; //0 -> disponivel, 1 -> reservado a pedido, 2 -> reservado a leilao

    public Servidor(String nome, double preco, String tipo, int estado){
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.estado = estado;
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

}
