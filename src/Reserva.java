public class Reserva {
    private int id;
    private String nome;
    private String tipo;
    private int estado; //0 - inativa, 1 - ativa
    private String email;

    public Reserva(int id, String nome, String tipo, int estado, String email){
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.estado = estado;
        this.email = email;
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
}
