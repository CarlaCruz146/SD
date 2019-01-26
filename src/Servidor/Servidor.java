package Servidor;

/**
 * Classe dos servidores.
 * @author Grupo 24
 */
public class Servidor {
    /** Nome do servidor */
    private String nome;
    /** Preço do servidor */
    private double preco;
    /** Tipo do servidor */
    private String tipo;
    /** Estado do servidor 0 - disponível, 1 - reservado a pedido, 2 - reversado a leilão*/
    private int estado;

    /**
     * Construtor da classe Servidor com parâmetros.
     * @param nome Nome do servidor
     * @param preco Preço do servidor
     * @param tipo Tipo do servidor
     * @param estado Estado do servidor
     */
    public Servidor(String nome, double preco, String tipo, int estado){
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.estado = estado;
    }

    /**
     * Devolve o nome do servidor.
     * @return nome
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Devolve o preço do servidor.
     * @return preço
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Devolve o tipo do servidor.
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devolve o estado do servidor.
     * @return estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Atualiza o nome do servidor.
     * @param nome Novo nome do servidor
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atualiza o preço do servidor.
     * @param preco Novo preço do servidor
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Atualiza o tipo do servidor.
     * @param tipo Novo tipo do servidor
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Atualiza o estado do servidor.
     * @param estado Novo estado do servidor
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

}
