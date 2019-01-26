package Servidor;

import java.time.LocalDateTime;
/**
 * Classe das reservas.
 * @author Grupo 24
 */
public class Reserva {
    /** Id da reserva */
    private int id;
    /** Nome do servidor reservado */
    private String nome;
    /** Tipo do servidor reservado */
    private String tipo;
    /** Estado da reserva */
    private int estado;
    /** Email do utilizador que efetuou a reserva */
    private String email;
    /** Preço da reserva 0 - inativo e 1 - ativo*/
    private double preco;
    /** Data do ínicio da reserva */
    private LocalDateTime inicioReserva;
    /** Data de cancelamento da reserva */
    private LocalDateTime fimReserva;

    /**
     * Construtor da classe Reserva com parâmetros.
     * @param id Id da reserva
     * @param nome Nome do servidor reservado
     * @param tipo Tipo do servidor reservado
     * @param estado Estado da reserva
     * @param email Email do utilizador que fez a reserva
     * @param preco Preço da reserva
     */
    public Reserva(int id, String nome, String tipo, int estado, String email, double preco){
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.estado = estado;
        this.email = email;
        this.preco = preco;
        this.inicioReserva = LocalDateTime.now();
        this.fimReserva = null;
    }

    /**
     * Devolve o id da reserva.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Devolve o email do utilizador que fez a reserva.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devolve o tipo de servidor reservado.
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devolve o nome do servidor reservado.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve o estado da reserva.
     * @return estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Devolve o preço da reserva.
     * @return preço
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Devolve a data de ínicio da reserva.
     * @return data
     */
    public LocalDateTime getInicioReserva(){
        return inicioReserva;
    }

    /**
     * Devolve a data de cancelamento da reserva.
     * @return data
     */
    public LocalDateTime getFimReserva(){
        return fimReserva;
    }

    /**
     * Atualiza o id da reserva.
     * @param id Novo id da reserva
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atualiza o email do utilizador que efetuou a reserva.
     * @param email Novo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Atualiza o nome do servidor reservado.
     * @param nome Novo nome do servidor
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Atualiza o tipo do servidor reservado.
     * @param tipo Novo tipo de servidor
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Atualiza o estado da reserva.
     * @param estado Novo estado da reserva
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Atualiza o preço da reserva.
     * @param preco Novo preço da reserva
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Atualiza a data de início da reserva.
     * @param inicioReserva Nova data
     */
    public void setInicioReserva(LocalDateTime inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    /**
     * Atualiza a data de cancelamento da reserva.
     */
    public void setFimReserva(LocalDateTime fimReserva) {
        this.fimReserva = fimReserva;
    }
}
