import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCloud {
    private Map<String,Utilizador> utilizadores;
    private Lock utilizadorLock;

    public ServerCloud(){
        this.utilizadorLock = new ReentrantLock();
        this.utilizadores = new HashMap<>();
    }

    public Utilizador iniciarSessao(String email, String password) throws PedidoInvalidoException{
        Utilizador u;
        utilizadorLock.lock();
        try {
            u = utilizadores.get(email);
            if (u == null || !u.verificaPassword(password)) throw new PedidoInvalidoException("Dados incorretos");
        }finally {
                utilizadorLock.unlock();
            }

            return u;
    }

    public void registar(String email, String password) throws UtilizadorExistenteException{
        utilizadorLock.lock();
        try{
            if(utilizadores.containsKey(email))
                throw new UtilizadorExistenteException("O email já existe");
            else utilizadores.put(email,new Utilizador(email,password));
        }
        finally {
            utilizadorLock.unlock();
        }
    }
}
