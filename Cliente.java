import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;


class Acoes {

    private Personagem personagem;
    private Scanner s = new Scanner(System.in);

    public Acoes(Personagem personagem){
        this.personagem = personagem;
    }

    public String hubAcoes(){

        String linhas [] = {
            "",
            "",
            "Qual ação deseja realizar?",
            "Digite apenas o número da opção desejada.",
            "",
            "\t(1) Atualizar meu estado atual",
            "\t(2) Realizar um teste",
            "\t(3) Realizar um dano",
            "",
        };

        for(String l: linhas){System.out.println(l);}

        
        System.out.print("Selecione sua opção: ");
        int opcao = s.nextInt();


        while(opcao != 1 || opcao != 2 || opcao !=3){

            switch(opcao){
                case 1:
                return mudarEstado();
                
                case 2:
                return realizarTeste();
                
                case 3:
                return realizarDano();
            }

            System.out.print("Opção inválida, digite novamente: ");
            opcao = s.nextInt();

        } 

        return null;
    }


    public String mudarEstado(){ return "\n" + personagem.nome + "realizou Mudar estado"; }
    
    public String realizarTeste(){ return "\n" + personagem.nome + "realizou Realizar Teste"; }
    
    public String realizarDano(){ return "\n" + personagem.nome + "realizou Dano"; }

}


class Personagem {

    //Características
    public String nome;
    public String estado;

    //Atributos
    public int f; //FOR
    public int i; //INT
    public int a; //AGI
    public int m; //MIS
    public int c; //CAR
    public int p; //PRO
    
    //Scanner
    private Scanner s = new Scanner(System.in);

    public Personagem(){

        System.out.println("\nDigite as características do seu personagem conforme forem solicitadas e tecle ENTER ao finalizar.\n");
        
        System.out.print("Nome: ");
        nome = s.nextLine(); 

        System.out.print("Estado Atual: ");
        estado = s.nextLine(); 


        System.out.println("\n\nDigite os atributos do seu personagem conforme forem solicitadas e tecle ENTER ao finalizar.\n");
        
        System.out.print("FOR: ");
        f = s.nextInt();
        
        System.out.print("INT: ");
        i = s.nextInt();
        
        System.out.print("AGI: ");
        a = s.nextInt();
        
        System.out.print("MIS: ");
        m = s.nextInt();
        
        System.out.print("CAR: ");
        c = s.nextInt();
        
        System.out.print("PRO: ");
        p = s.nextInt();
        
        System.out.println("\n\nCriação de personagem concluída!\n");
    }

}


class Recebedor implements Runnable {

    private InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {
        
        //Instancia um scanner para mensagens vindas do servidor
        Scanner s = new Scanner(this.servidor);

        //Exibe todas as mensagens vindas do servidor
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }

        s.close();
    }
}


public class Cliente {

    private static String host = "127.0.0.1";
    private static int porta = 12345;
    private static Personagem personagem; 
    private static Acoes acoes;

    public static void main(String[] args) throws UnknownHostException, IOException {    
        
        executa();
    }

    public static void executa() throws UnknownHostException, IOException {

        //Cria um novo cliente
        try (Socket cliente = new Socket(host, porta)) {
            
            //Cria uma thread para receber mensagens do servidor
            Recebedor r = new Recebedor(cliente.getInputStream());
            new Thread(r).start();

            //Lê msgs do teclado e manda pro servidor
            // Scanner teclado = new Scanner(System.in);
            PrintStream saida = new PrintStream(cliente.getOutputStream());

            //Cria um novo personagem e instancia suas acoes
            personagem = new Personagem();
            acoes = new Acoes(personagem);

            while (true) {
                String a = acoes.hubAcoes();
                saida.println(a);
            }
        }
        
        // saida.close();
        // teclado.close();
        // cliente.close();
    }
}