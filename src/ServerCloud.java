import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCloud {
    private Map<String,Utilizador> utilizadores;
    private Map<String,List<Servidor>> servidores;
    private Map<Integer,Reserva> reservas;
    private Lock utilizadorLock;

    public ServerCloud(){
        this.utilizadorLock = new ReentrantLock();
        this.utilizadores = new HashMap<>();
        this.servidores = new HashMap<>();
        this.reservas = new HashMap<>();
        this.addServidores();
    }

    public void addServidores(){
        Servidor servidor = new Servidor("t1", 1, "Pequeno", 0);
        Servidor servidor1 = new Servidor("t2", 1, "Pequeno", 0);
        Servidor servidor2 = new Servidor("t3", 1, "Pequeno", 0);
        List<Servidor> s = new ArrayList<>();
        s.add(servidor);
        s.add(servidor1);
        s.add(servidor2);
        Servidor servidor3 = new Servidor("m1", 3, "Grande", 0);
        Servidor servidor4 = new Servidor("m2", 3, "Grande", 0);
        List<Servidor> s2 = new ArrayList<>();
        s2.add(servidor3);
        s2.add(servidor4);
        this.servidores.put(servidor.getTipo(),s);
        this.servidores.put(servidor3.getTipo(),s2);
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

    public Servidor verificaDisponibilidade (String tipo){
        Servidor leilao = null;
        List<Servidor> servidores = this.servidores.get(tipo);
        for(Servidor s: servidores)
            if(s.getEstado()==0) return s;
            else if (s.getEstado()==2) leilao = s;
        return leilao;
    }

    public void cancelaServidorLeilao(Servidor s){
        for (Reserva r : this.reservas.values())
            if(r.getTipo().equals(s.getTipo()) && r.getNome().equals(s.getNome())) {
                r.setEstado(0);
                this.reservas.put(r.getId(),r);
                break;
            }
    }

    public int pedirServidor(Utilizador u, String tipo) throws ServidorInexistenteException{
        for(List<Servidor> ree : this.servidores.values())
            for(Servidor s: ree){
                System.out.println("Estado " + s.getEstado());
            }
         Servidor servidor = verificaDisponibilidade(tipo);
         if(servidor == null)
            throw new ServidorInexistenteException("Não existem servidores disponíveis");
        else {
            if(servidor.getEstado()==2) {
                this.cancelaServidorLeilao(servidor);
            }
            String nomeS = servidor.getNome();
            Reserva r = new Reserva(reservas.size(), nomeS, tipo, 1, u.getEmail());
            this.reservas.put(r.getId(), r);
            servidor.setEstado(1);
            return r.getId();
         }
    }

    public void cancelarServidor(int id, Utilizador u) throws ReservaInexistenteException{
        if(!this.reservas.containsKey(id))
            throw new ReservaInexistenteException("Reserva inexistente");
        else {
            Reserva r = this.reservas.get(id);
            if(!r.getEmail().equals(u.getEmail()))
                throw new ReservaInexistenteException("Reserva inexistente");
            r.setEstado(0);
            List<Servidor> serv = this.servidores.get(r.getTipo());
            for(Servidor s : serv) {
                if (s.getNome().equals(r.getNome()))
                    s.setEstado(0);
            }
        }
    }
}
