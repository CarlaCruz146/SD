public class Utilizador {
    private final String email;
    private final String password;

    private double divida;

    Utilizador(String email, String password) {
        this.email = email;
        this.password = password;
        this.divida = 0;
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

    public double getDivida() {
        return this.divida;
    }
    public void setDivida(double v){
        this.divida = v;
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
