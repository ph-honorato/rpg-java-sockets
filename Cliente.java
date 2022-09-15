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

    public static void main(String[] args) throws UnknownHostException, IOException {    
        // dispara cliente
        executa();
    }

    public static void executa() throws UnknownHostException, IOException {
        
        Socket cliente = new Socket(host, porta);
        System.out.println("O cliente se conectou ao servidor!");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();
        
        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
        saida.close();
        teclado.close();
        cliente.close();
    }
}