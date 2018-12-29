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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public int getEstado() {
        return estado;
    }

    public double getPreco() {
        return preco;
    }

    public LocalDateTime getInicioReserva(){
        return inicioReserva;
    }

    public LocalDateTime getFimReserva(){
        return fimReserva;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setInicioReserva(LocalDateTime inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    public void setFimReserva(LocalDateTime fimReserva) {
        this.fimReserva = fimReserva;
    }
}

