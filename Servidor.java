import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Historia {

    public String escolherPersonagem(){
        String [] linhas = {
            "",
            "",
            "",
            "",
            "Existem quatro opções de classe:",
            "",
            "Arqueiro (1)\tBárbaro (2)\tCavaleiro (3)\tMago (4)",
            "",
            "Vida: 20\tVida: 25\tVida: 30\tVida: 15",
            "Ataque: 1xD8\tAtaque: 1xD10\tAtaque: 1xD6\tAtaque: 1xD6",
            "Ação: Mirar\tAção: Fúria\tAção: Defender\tAção: Curar",
            "",
            "",
            "",
        };

        String texto = "";
        for (String l: linhas){texto += l + '\n';}
        return texto;
    }

    public void personagemEscolhido(String p){
        String [] linhas = {
            "",
            "Você se conectou utilizando a classe: " + p,
            "",
        };

        for (String l: linhas){System.out.println(l);}
    }

    public void inicio(){
        
    }
}


class TrataCliente implements Runnable {
    
    private InputStream cliente;
    private Servidor servidor;

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.cliente);
        
        while (s.hasNextLine()) {
            servidor.distribuirMensagem(s.nextLine());
        }
        
        s.close();
    }
}



public class Servidor {
     
    //Propriedades do Servidor
    private int maxJogadores = 2;
    private int numJogadores = 0;
    private int porta = 12345;
    private List<PrintStream> clientes = new ArrayList<PrintStream>();
    
    //História
    public static Historia h = new Historia();

    public void executa () throws IOException {

        try (ServerSocket servidor = new ServerSocket(porta)) {
            
            System.out.println("Porta " + porta + " aberta!");
            boolean accept = true;

            while (accept) {

                //Aceita um cliente
                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                //Adiciona saida do cliente à lista 
                PrintStream ps = new PrintStream(cliente.getOutputStream());
                clientes.add(ps);

                //Cria tratador de cliente numa nova thread 
                TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
                new Thread(tc).start();

                //soma no número de jogadores atuais
                this.numJogadores++;

                if(numJogadores == maxJogadores)
                    accept = false;  
                
            }

            distribuirMensagem(h.escolherPersonagem());

        }
    }

    public void distribuirMensagem(String msg) {
        // envia msg para todo mundo
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        
        // inicia o servidor
        new Servidor().executa();
    
    }
}
