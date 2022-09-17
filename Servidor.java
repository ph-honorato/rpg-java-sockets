import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        
        //Instancia um scanner para o cliente
        Scanner s = new Scanner(this.cliente);

        //Distribui a mensagem pra todos os outros clientes
        while (s.hasNextLine()) {
            servidor.distribuiMensagem(s.nextLine());
        }

        s.close();
    }
}



public class Servidor {

    //Porta para abrir o servidor
    private int porta = 12345;

    //Vetor de clientes
    private List<PrintStream> clientes = new ArrayList<PrintStream>();;

    public void executa() throws IOException {

        //Abre o servidor
        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("Porta " + porta + " aberta!");

            while (true) {
                //Aceito um cliente
                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                //Adiciona saida do cliente à lista 
                PrintStream ps = new PrintStream(cliente.getOutputStream());
                this.clientes.add(ps);

                //Cria tratador de cliente em uma nova thread 
                TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
                new Thread(tc).start();
            }
        }
    }

    public void distribuiMensagem(String msg) {
        //Envia a memsagem para todos os clientes
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        //Inicia o servidor
        new Servidor().executa();
    }
}