package Servidor;

/**
 * Classe dos utilizadores.
 * @author Grupo 24
 */
public class Utilizador {

    /** Email do utilizador */
    private final String email;
    /** Password do utilizador */
    private final String password;
    /** Buffer de mensagens do utilizador */
    private MensagemBuffer msg;

    /**
     * Construtor da classe Utilizador com parâmetros.
     * @param email Email do utilizador
     * @param password Password do utilizador
     */
    Utilizador(String email, String password) {
        this.email = email;
        this.password = password;
        this.msg = new MensagemBuffer();
    }

    /**
     * Verifica se a password dada corresponde à password do utilizador.
     * @param password Password
     * @return Se corresponde ou não
     */
    public boolean verificaPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Devolve o email do utilizador.
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Devolve a password do utilizador.
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Escreve uma mensagem no buffer de mensagens do utilizador.
     * @param m Mensagem a notificar
     */
    public void notificar(String m) {
        msg.write(m);
    }

    /**
     * Devolve a última notificação ao utilizador.
     * @return notificação
     */
    public String lerNotificacao() throws InterruptedException{
        return msg.read();
    }

    /**
     * Atualiza as notificações do utilizador.
     * @param m Novo buffer de mensagens
     */
    public void setNotificacoes(MensagemBuffer m){
        this.msg = m;
    }

    /**
     * Função equals da classe Utilizador.
     * @param o Objecto
     * @return boolean
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || (this.getClass() != o.getClass()))
            return false;

        Utilizador usr = (Utilizador) o;
        return email.equals(usr.email);
    }
}
