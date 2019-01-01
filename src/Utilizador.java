public class Utilizador {
    private final String email;
    private final String password;
    private MensagemBuffer msg;

    Utilizador(String email, String password) {
        this.email = email;
        this.password = password;
        this.msg = new MensagemBuffer();
    }

    public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void notificar(String m) {
        msg.write(m);
    }

    public String lerNotificacao() throws InterruptedException{
        return msg.read();
    }

    public void setNotificacoes(MensagemBuffer m){
        this.msg = m;
    }
    
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || (this.getClass() != o.getClass()))
            return false;

        Utilizador usr = (Utilizador) o;
        return email.equals(usr.email);
    }
}
