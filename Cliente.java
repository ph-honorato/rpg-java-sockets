import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

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
    //private static Personagem personagem; 
    //private static Acoes acoes;

    public static void main(String[] args) throws UnknownHostException, IOException {    
        
        executa();
    }

    public static void executa() throws UnknownHostException, IOException {

        //Cria um novo cliente
        Socket cliente = new Socket(host, porta);

        //Cria uma thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        //Lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        //Cria um novo personagem e suas acoes
        //personagem = new Personagem();
        //acoes = new acoes(personagem);

        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
        
        saida.close();
        teclado.close();
        cliente.close();
    }
}