public class Utilizador {
    private final String email;
    private final String password;

    Utilizador(String email, String password) {
        this.email = email;
        this.password = password;
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
    
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || (this.getClass() != o.getClass()))
            return false;

        Utilizador usr = (Utilizador) o;
        return email.equals(usr.email);
    }
}
