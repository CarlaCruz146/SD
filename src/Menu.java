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
                    "* 0 - Terminar Sessao                        *\n"+
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
