import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

class personagem {

    public String classe;
    public int maxVida;
    public int atualVida;
    public int dano;
    public String acao;

    public personagem(String classe, int maxVida, int atualVida, int dano, String acao){
        this.classe = classe;
        this.maxVida = maxVida;
        this.atualVida = atualVida;
        this.dano = dano;
        this.acao = acao;
    }

}

class Recebedor implements Runnable {
    
    private InputStream servidor;
    
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }
    
    public void run() {
        // recebe msgs do servidor e imprime na tela
        Scanner s = new Scanner(this.servidor);
        
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }

        s.close();
    }
}


public class Cliente {

    private static String host = "127.0.0.1";
    private static int porta = 12345;
    public static Scanner teclado = new Scanner(System.in);

    public static personagem p;


    public static void executa() throws UnknownHostException, IOException {
        
        Socket cliente = new Socket(host, porta);
        System.out.println("Aguardando outros jogadores....");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();
        
        // lê msgs do teclado e manda pro servidor
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
        saida.close();
        cliente.close();
    }

    public static personagem escolherPersonagem(){
        
        personagem [] p = {
            new personagem("Arqueiro", 20, 20, 8, "mirar"),
            new personagem("Bárbaro", 25, 25, 10, "furia"),
            new personagem("Cavaleiro", 30, 30, 6, "defender"),
            new personagem("Mago", 15, 15, 6, "mago"),
        };

        System.out.print("Digite sua opção de classe: ");
        int opcao = teclado.nextInt();
        
        while (opcao != 1 || opcao != 2 || opcao != 3 || opcao != 4){
            if(opcao == 1)
                return p[0]; 
            else if(opcao == 2)
                return p[1]; 
            else if(opcao == 3)
                return p[2]; 
            else if(opcao == 4)
                return p[3];

            System.out.print("Opção inválida, digite novamente: ");
            opcao = teclado.nextInt();
        }
        
        return null;
    }

    public static void main(String[] args) throws UnknownHostException, IOException {    
        
        // p = escolherPersonagem();

        // dispara cliente
        executa();
    }
}