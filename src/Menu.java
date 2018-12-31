import java.util.Scanner;

public class Menu {
    // variáveis de instância
    private int op;
    private Scanner in;

    Menu() {
        in = new Scanner(System.in);
        this.op = 0;
    }
    /** Apresentar o menu */
    public void showMenu() {
        switch(op){
            case 0: System.out.println("************* MENU ****************\n"+
                    "* 1 - Iniciar Sessao              *\n"+
                    "* 2 - Registar utilizador         *\n"+
                    "* 0 - Sair                        *\n"+
                    "***********************************\n");
            break;
            case 1: System.out.println("************* MENU ****************\n"+
                    "* 1 - Reservar servidor a pedido    *\n"+
                    "* 2 - Cancelar servidor             *\n"+
                    "* 3 - Consultar reservas ativas     *\n"+
                    "* 4 - Consultar dívida              *\n"+
                    "* 5 - Reservar servidor em leilão   *\n"+
                    "* 0 - Terminar Sessao               *\n"+
                    "***********************************\n");
            break;
            case 2: System.out.println("********* MENU RESERVA *************\n"+
                    "* 1 - Pequeno                     *\n"+
                    "* 2 - Grande                      *\n"+
                    "* 0 - Cancelar                    *\n"+
                    "***********************************\n");
            break;
            case 3: System.out.println("********* MENU RESERVA *************\n"+
                    "* 1 - Pequeno a Leilão            *\n"+
                    "* 2 - Grande a Leilão             *\n"+
                    "* 0 - Cancelar                    *\n"+
                    "***********************************\n");
            break;
            case 4: System.out.println("********* MENU RESERVA *************\n"+
                    "* 1 - Inserir valor da proposta   *\n"+
                    "* 0 - Cancelar                    *\n"+
                    "***********************************\n");
                break;
        }System.out.println("Escolha uma opção");
    }

    public int readOp() {
        int n;

        try {
            n = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n>Valor inválido\n");
            n = -1;
        }

        return n;
    }

    public String readString(String mensagem) {
        System.out.println(mensagem);
        return in.nextLine();
    }

    public Integer op() {
        int opcao = readOp();
        if (op == 0) {
            while (opcao < 0 || opcao > 2) {
                System.out.println("Escolha uma opção: ");
                opcao = readOp();
            }
        }
        return opcao;
    }

    public int getOp(){
        return this.op;
    }

    public void setOp(int n){
        this.op = n;
    }
}
