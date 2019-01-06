import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private String nome;
    private String tipo;
    private int estado; //0 - inativa, 1 - ativa
    private String email;
    private double preco;
    private LocalDateTime inicioReserva; // data e hora a que foi feita a reserva
    private LocalDateTime fimReserva; //data a que foi libertado

    /**
     * Construtor da classe Reserva com paramêtros.
     * @param id Id da reserva.
     * @param nome Nome da reserva.
     * @param tipo Tipo da reserva.
     * @param estado Estado da reserva.
     * @param email Email do Cliente.
     * @param preco Preço da reserva.
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
     * Devolve o email da reserva.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Devolve o tipo da reserva.
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devolve o nome da reserva.
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
     * @return preco
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Devolve o inicio da reserva.
     * @return inicioReserva
     */
    public LocalDateTime getInicioReserva(){
        return inicioReserva;
    }

    /**
     * Devolve o fim da reserva.
     * @return fimReserva
     */
    public LocalDateTime getFimReserva(){
        return fimReserva;
    }

    /**
     * Altera o id da reserva.
     * @param id Id da reserva
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Altera o email da reserva.
     * @param email Email da reserva
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Altera o nome da reserva.
     * @param nome Nome da reserva
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Altera o tipo da reserva.
     * @param tipo Tipo da reserva
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Altera o estado da reserva.
     * @param estado Estado da reserva
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Altera o preço da reserva.
     * @param preco Preço da reserva
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Altera o inicio da reserva.
     * @param inicioReserva Inicio da reserva
     */
    public void setInicioReserva(LocalDateTime inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    /**
     * Altera o fim da reserva.
     * @param fimReserva Fim da reserva
     */
    public void setFimReserva(LocalDateTime fimReserva) {
        this.fimReserva = fimReserva;
    }
}

