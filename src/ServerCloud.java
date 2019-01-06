import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCloud {
    private Map<String, Utilizador> utilizadores;
    private Map<String, List<Servidor>> servidores;
    private Map<Integer, Reserva> reservas;
    private Map<Integer, Leilao> leiloes;
    private Lock utilizadorLock;
    private Lock servidorLock;
    private Lock reservaLock;
    private Lock leilaoLock;
    private Condition conditionS;

    //ver se e lockleilao ou servidorlock
    public ServerCloud() {
        this.reservaLock = new ReentrantLock();
        this.servidorLock = new ReentrantLock();
        this.leilaoLock = new ReentrantLock();
        this.utilizadorLock = new ReentrantLock();
        this.utilizadores = new HashMap<>();
        this.servidores = new HashMap<>();
        this.reservas = new HashMap<>();
        this.leiloes = new HashMap<>();
        this.conditionS = leilaoLock.newCondition();
        this.addServidores();
    }

    public void addServidores() {
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
        this.servidores.put(servidor.getTipo(), s);
        this.servidores.put(servidor3.getTipo(), s2);
    }

    public Utilizador iniciarSessao(String email, String password, MensagemBuffer msg) throws PedidoInvalidoException {
        Utilizador u;
        utilizadorLock.lock();
        try {
            u = utilizadores.get(email);
            if (u == null || !u.verificaPassword(password)) throw new PedidoInvalidoException("Dados incorretos");
            else u.setNotificacoes(msg);
        } finally {
            utilizadorLock.unlock();
        }

        return u;
    }

    public void registar(String email, String password) throws UtilizadorExistenteException {
        utilizadorLock.lock();
        try {
            if (utilizadores.containsKey(email))
                throw new UtilizadorExistenteException("O email já existe");
            else utilizadores.put(email, new Utilizador(email, password));
        } finally {
            utilizadorLock.unlock();
        }
    }

    public Servidor verificaDisponibilidade(String tipo) {
        Servidor servidor = null;
        servidorLock.lock();
        try {
            List<Servidor> servidores = this.servidores.get(tipo);
            for (Servidor s : servidores)
                if (s.getEstado() == 0) {
                    servidor = s;
                    servidor.setEstado(1);
                    break;
                } else if (s.getEstado() == 2) {
                    servidor = s;
                }
            if (servidor != null && servidor.getEstado() == 2) {
                servidor.setEstado(1);
                this.cancelaServidorEmLeilao(servidor);

            }
        } finally {
            servidorLock.unlock();
        }
        return servidor;
    }

    public void cancelaServidorEmLeilao(Servidor s) {
        for (Reserva r : this.reservas.values())
            if (r.getTipo().equals(s.getTipo()) && r.getNome().equals(s.getNome()) && r.getEstado()==1) {
                utilizadorLock.lock();
                try {
                    Utilizador u = this.utilizadores.get(r.getEmail());
                    u.notificar("A reserva com ID " + r.getId() + " foi cancelada.");
                } finally {
                    utilizadorLock.unlock();
                }
                reservaLock.lock();
                try {
                    r.setEstado(0);
                    r.setFimReserva(LocalDateTime.now());
                    this.reservas.put(r.getId(), r);
                } finally {
                    reservaLock.unlock();
                }
                break;
            }
    }

    public int pedirServidor(Utilizador u, String tipo) throws ServidorInexistenteException {
        int idR;
        Servidor servidor = verificaDisponibilidade(tipo);
        if (servidor == null)
            throw new ServidorInexistenteException("Não existem servidores disponíveis");
        else {
            String nomeS = servidor.getNome();
            reservaLock.lock();
            try {
                idR = reservas.size();
                Reserva r = new Reserva(idR, nomeS, tipo, 1, u.getEmail(), servidor.getPreco());
                this.reservas.put(idR, r);
            } finally {
                reservaLock.unlock();
            }

            return idR;
        }
    }

    public void cancelarServidor(int id, Utilizador u) throws ReservaInexistenteException, LeilaoInexistenteException {
        reservaLock.lock();
        try {
            if (!this.reservas.containsKey(id)) {
                System.out.println(this.reservas.containsKey(id));
                throw new ReservaInexistenteException("Reserva inexistente");
            } else {
                Reserva r = this.reservas.get(id);
                if (!r.getEmail().equals(u.getEmail()))
                    throw new ReservaInexistenteException("Reserva inexistente");
                r.setEstado(0);
                r.setFimReserva(LocalDateTime.now());
                servidorLock.lock();
                try {
                    List<Servidor> serv = this.servidores.get(r.getTipo());
                    for (Servidor s : serv) {
                        if (s.getNome().equals(r.getNome())) {
                            s.setEstado(0);
                            encerraLeilao(s.getTipo(), s);
                            break;
                        }
                    }
                } finally {
                    servidorLock.unlock();
                }
            }
        } finally {
            reservaLock.unlock();
        }
    }

    public double dividaAtual(Utilizador u) {
        double val = 0;
        for (Reserva r : reservas.values()) {
            if (r.getEmail().equals(u.getEmail()))
                val += calculaDivida(r);
        }
        return val;
    }

    private double calculaDivida(Reserva r) {
        LocalDateTime inicio = r.getInicioReserva();
        LocalDateTime fim;
        double res = 0;

        if (r.getEstado() == 0) fim = r.getFimReserva();
        else fim = LocalDateTime.now();

        long horas = ChronoUnit.HOURS.between(inicio, fim);
        long minutos = ChronoUnit.MINUTES.between(inicio, fim);
        if (minutos % 60 == 0)
            res += horas * r.getPreco();
        else res += (horas + 1) * r.getPreco();
        return res;
    }

    public List<Reserva> reservasAtivas(Utilizador u) {
        List<Reserva> rs = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (r.getEmail() == u.getEmail()) {
                if (r.getEstado() == 1)
                    rs.add(r);
            }
        }
        return rs;
    }


    public void proposta(int idLeilao, Utilizador u, double preco) throws LicitacaoInvalidaException, LeilaoInexistenteException {
        Leilao l = getLeilao(idLeilao);
        l.proposta(u, preco);
    }

    private Leilao getLeilao(int idLeilao) throws LeilaoInexistenteException {
        Leilao l;
        leilaoLock.lock();
        try {
            l = leiloes.get(idLeilao);
            if (l == null) throw new LeilaoInexistenteException("Leilão inexistente");
        } finally {
            leilaoLock.unlock();
        }
        return l;
    }

    public int iniciaLeilao(String tipo) {
        int idLeilao;
        leilaoLock.lock();
        try {
            idLeilao = leiloes.size();
            Leilao l = new Leilao(idLeilao, tipo);
            leiloes.put(idLeilao, l);
        } finally {
            leilaoLock.unlock();
        }
        return idLeilao;
    }

    public void encerraLeilao(String tipo, Servidor s) throws LeilaoInexistenteException {
        int idLeilao = -1;
        Lance lance;
        for (Leilao l : this.leiloes.values())
            if (l.getTipo().equals(tipo)) {
                idLeilao = l.getId();
                break;
            }
        if (idLeilao != -1) {
            lance = vencedorLeilao(idLeilao);
            reservaLock.lock();
            try {
                int idR;
                idR = reservas.size();
                s.setEstado(2); 
                Utilizador u = lance.getComprador();
                Reserva r = new Reserva(idR, s.getNome(), tipo, 1, u.getEmail(), lance.getValor());
                this.reservas.put(idR, r);
            } finally {
                reservaLock.unlock();
            }
        }

    }

    public Lance vencedorLeilao(int idLeilao) throws LeilaoInexistenteException {
        Leilao l;
        leilaoLock.lock();
        try {
            l = getLeilao(idLeilao);
            leiloes.remove(idLeilao);
        } finally {
            leilaoLock.unlock();
        }
        return l.terminaLeilao();
    }

    public Servidor verificaDisponibilidadeLeilao(String tipo) {
        Servidor servidor = null;
        List<Servidor> servidores;
        servidorLock.lock();
        try {
            servidores = this.servidores.get(tipo);
            for (Servidor s : servidores)
                if (s.getEstado() == 0) {
                    servidor = s;
                    servidor.setEstado(2);
                    break;
                }
        } finally {
            servidorLock.unlock();
        }
        return servidor;
    }

    public int reservarLeilao(double valor, String tipo, Utilizador u) throws InterruptedException, LicitacaoInvalidaException, LeilaoInexistenteException {
        Servidor s;
        int idR = -1;
        if ((s = verificaDisponibilidadeLeilao(tipo)) != null) {
            reservaLock.lock();
            try {
                idR = reservas.size();
                Reserva r = new Reserva(idR, s.getNome(), tipo, 1, u.getEmail(), valor);
                this.reservas.put(idR, r);
            } finally {
                reservaLock.unlock();
            }
        } else {
            int idLeilao = -1;
            for (Leilao l : this.leiloes.values())
                if (l.getTipo().equals(tipo)) {
                    idLeilao = l.getId();
                    break;
                }
            if (idLeilao == -1) {
                idLeilao = this.iniciaLeilao(tipo);
            }
            this.proposta(idLeilao, u, valor);
        }
        return idR;
    }

    public List<Servidor> getServidoresAtivos() {
        List<Servidor> r = new ArrayList<>();
        for (Servidor s : servidores.get("Pequeno"))
            if (s.getEstado() == 0) {
                r.add(s);
            }
        for (Servidor s : servidores.get("Grande"))
            if (s.getEstado() == 0) {
                r.add(s);
            }
        return r;
    }
}
