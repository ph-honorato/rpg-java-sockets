import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

import javax.lang.model.element.Element;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Personagem {
    // Características
    public String nome;
    public String estado;

    // Atributos
    public int f; // FOR
    public int i; // INT
    public int d; // DES
    public int m; // MIS
    public int c; // CAR
    public int p; // PRO

    public void exibir(){
        System.out.println(""+
        "nome: "+ nome + "\n" +
        "estado: "+ estado + "\n" +
        "\n" +
        "FOR : "+ f + "\n" +
        "INT : "+ i + "\n" +
        "DES : "+ d + "\n" +
        "MIS : "+ m + "\n" +
        "CAR : "+ c + "\n" +
        "PRO : "+ p + "\n"
        );
    }

    public void teste(String teste){

        int resultado = 0;
        System.out.println(nome + " obteu  { " + resultado + " }  como resultado de um teste de " + teste);

    }
}

class Acoes {

    private Personagem personagem;
    private boolean primeiroCadastro = false;

    private String estados [] = {"Normal", "Cansado", "Exausto", "Enjoado"};

    private String testes [] = {
        "Combate", "Resistência", "Intimidação",
        "Magia", "Intelecto", "Percepção",
        "Agilidade", "Furtividade", "Mira",
        "Fé", "Encantamento", "Sanidade",
        "Carisma", "Arte", "Sorte",
        "Sobrevivência", "Cutelaria", "Ciências",
    };

    public Acoes(Personagem personagem) {
        this.personagem = personagem;
        Gui();
    }

    public void Gui() {

        JFrame f = new JFrame("RPG");
        f.setVisible(true);
        f.setSize(420, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container p = new Container();
        p.setBackground(Color.WHITE);

        //Algumas definições para o form
        int fontSize = 10; 
        int labelSize = 35;
        int labelHeight = 20;
        int fieldSize = 100;
        int fieldHeight = 20;
        int space = 10;
        int initialX = 20;
        int initialY = 30;

        
        //Nome
        JLabel lnome = new JLabel("Nome");
        lnome.setFont(new Font("Arial", Font.PLAIN, fontSize));
        lnome.setSize(labelSize, labelHeight);
        lnome.setLocation(initialX, initialY);
        p.add(lnome);
 
        JTextField fnome = new JTextField();
        fnome.setSize(fieldSize, fieldHeight);
        fnome.setLocation(initialX + labelSize + space, initialY);
        p.add(fnome);


        //Estado
        JLabel lestado = new JLabel("Estado");
        lestado.setFont(new Font("Arial", Font.PLAIN, fontSize));
        lestado.setSize(labelSize, labelHeight);
        lestado.setLocation(200, initialY);
        p.add(lestado);
 
        JComboBox<String> festado = new JComboBox<String>(estados);
        festado.setSize(fieldSize, fieldHeight);
        festado.setLocation(200 + labelSize + space, initialY);
        p.add(festado);


        //Atributos
        JTextField ffor = new JTextField();
        JTextField fint = new JTextField();
        JTextField fdes = new JTextField();
        JTextField fmis = new JTextField();
        JTextField fcar = new JTextField();
        JTextField fpro = new JTextField();
        JLabel lfor = new JLabel("FOR");
        JLabel lint = new JLabel("INT");
        JLabel ldes = new JLabel("DES");
        JLabel lmis = new JLabel("MIS");
        JLabel lcar = new JLabel("CAR");
        JLabel lpro = new JLabel("PRO");
        
        int posX = 70;
        int posY = 100;
        int cont = 0;
        for(JTextField a: new JTextField [] {ffor, fint, fdes, fmis, fcar, fpro} ){
            if(cont == 3){
                posX = 70;
                posY += 80;
            }
            a.setSize(50, fieldHeight +10);
            a.setLocation(posX, posY);
            p.add(a);
            posX += 100;
            cont++;
        }
    
        posX = 85;
        posY = 130;
        cont = 0;
        for(JLabel a: new JLabel [] {lfor, lint, ldes, lmis, lcar, lpro} ){
            if(cont == 3){
                posX = 85;
                posY += 80;
            }
            a.setFont(new Font("Arial", Font.PLAIN, fontSize));
            a.setSize(50, fieldHeight +10);
            a.setLocation(posX, posY);
            p.add(a);
            posX += 100;
            cont++;
        }


        //Salvar
        JButton bsalvar = new JButton("Salvar");
        bsalvar.setSize(80, 20);
        bsalvar.setLocation(20, 350);
        p.add(bsalvar);
        
        bsalvar.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                try{
                    
                    String [] campos = {
                        ffor.getText().toString(),
                        fint.getText().toString(),
                        fdes.getText().toString(),
                        fmis.getText().toString(),
                        fcar.getText().toString(),
                        fpro.getText().toString(),
                    };
                    int [] traducoes = new int [6]; 

                    for(int x = 0; x < campos.length; x++){
                        if( campos[x].length() == 0){
                            traducoes[x] = 0;
                        
                        } else if(campos[x].length() == 1 && campos[x].matches("[0-9]")) {
                            traducoes[x] = Integer.parseInt(campos[x]);

                        } else if(campos[x].length() == 2 && (campos[x].charAt(0) +"").matches("\\-") && (campos[x].charAt(1) +"").matches("[0-9]")) {
                            traducoes[x] = Integer.parseInt(campos[x]);

                        } else {
                            traducoes[x] = 0;
                        }
                    }
                    
                    salvarPersonagem(
                        fnome.getText(), 
                        festado.getSelectedItem().toString(),
                        traducoes[0],
                        traducoes[1],
                        traducoes[2],
                        traducoes[3],
                        traducoes[4],
                        traducoes[5]
                    );

                    ffor.setText(traducoes[0] + "");
                    fint.setText(traducoes[1] + "");
                    fdes.setText(traducoes[2] + "");
                    fmis.setText(traducoes[3] + "");
                    fcar.setText(traducoes[4] + "");
                    fpro.setText(traducoes[5] + "");

                } catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
            }         
        });


        //Realizar Teste
        JComboBox<String> fteste = new JComboBox<String>(testes);
        fteste.setSize(fieldSize, fieldHeight);
        fteste.setLocation(175, 350);
        p.add(fteste);

        JButton bteste = new JButton("Realizar Teste");
        bteste.setSize(120, 20);
        bteste.setLocation(275, 350);
        p.add(bteste);

        bteste.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                
                if(primeiroCadastro){
                    personagem.teste(fteste.getSelectedItem().toString());
                
                } else {
                    String newNome = fnome.getText().length() == 0 ? "Personagem" : fnome.getText();
                    salvarPersonagem(newNome, festado.getSelectedItem().toString(), 0, 0, 0, 0, 0, 0);
                    fnome.setText(newNome);
                    ffor.setText("0");
                    fint.setText("0");
                    fdes.setText("0");
                    fmis.setText("0");
                    fcar.setText("0");
                    fpro.setText("0");
                    personagem.teste(fteste.getSelectedItem().toString());
                }
            }         
        });

        //Adiciona o painel a tela
        f.add(p); 
    }

    public void salvarPersonagem(String nome, String estado, int f, int i, int d, int m, int c, int p){
        personagem.nome = nome;
        personagem.estado = estado;
        personagem.f = f;
        personagem.i = i;
        personagem.d = d;
        personagem.m = m;
        personagem.c = c;
        personagem.p = p;

        primeiroCadastro = true;
    }

}

class Recebedor implements Runnable {

    private InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {

        // Instancia um scanner para mensagens vindas do servidor
        Scanner s = new Scanner(this.servidor);

        // Exibe todas as mensagens vindas do servidor
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

        // Cria um novo cliente
        try (Socket cliente = new Socket(host, porta)) {

            // Cria uma thread para receber mensagens do servidor
            Recebedor r = new Recebedor(cliente.getInputStream());
            new Thread(r).start();

            // Lê msgs do teclado e manda pro servidor
            Scanner teclado = new Scanner(System.in);
            PrintStream saida = new PrintStream(cliente.getOutputStream());

            // Cria um novo personagem e instancia suas acoes
            personagem = new Personagem();
            acoes = new Acoes(personagem);

            while (teclado.hasNextLine()) {
                saida.println(personagem.nome + " digitou: " + teclado);
            }
        }

        // saida.close();
        // teclado.close();
        // cliente.close();
    }
}