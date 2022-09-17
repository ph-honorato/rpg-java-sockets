import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.RunElement;
import javax.xml.soap.Text;

import java.awt.*;
import java.awt.event.*;


class Personagem {
    //Características
    public String nome;
    public String estado;

    public PrintStream saida;

    //Atributos
    public int f; // FOR
    public int i; // INT
    public int d; // DES
    public int m; // MIS
    public int c; // CAR
    public int p; // PRO

    public Personagem(PrintStream saida){
        this.saida = saida;
    }

    //Realizar Teste
    public void teste(String teste){
        int resultado = 0;
        int dados = 0;
        int mods = 0;
        Random aleatorio = new Random();
        int max = 20;
        int min = 1;
        String mensagem = "";
        
        if( !(this.estado == "Enlouquecido") ){
        
            if (teste == "Combate" || teste == "Resistência" || teste == "Intimidação"){ dados = this.f; } 
            else if (teste == "Magia" || teste == "Intelecto" || teste == "Percepção"){ dados = this.i; }
            else if (teste == "Agilidade" || teste == "Furtividade" || teste == "Mira"){ dados = this.d; } 
            else if (teste == "Fé" || teste == "Encantamento" || teste == "Sanidade"){ dados = this.m; } 
            else if (teste == "Persuasão" || teste == "Arte" || teste == "Sorte"){ dados = this.c; } 
            else if (teste == "Sobrevivência" || teste == "Cutelaria" || teste == "Ciências"){ dados = this.p; }
            
            if(this.estado == "Esgotado"){ dados = dados >= 0 ? 0 : dados; } 
            else if (this.estado == "Assustado" || this.estado == "Cansado" || this.estado == "Desprevenido"){ mods = -2; } 
            else if (this.estado == "Apavorado" || this.estado == "Exausto" || this.estado == "Enjoado"){ mods = -5;} 

            if(dados >= 0){
                dados = dados + 1;
                for(int x = 0; x < dados; x++){
                    int dado = aleatorio.nextInt(max - min + 1) + min;
                    resultado = dado > resultado ? dado : resultado;
                }
            
            } else {
                dados = (dados*-1) + 1;
                resultado = 20;
                for(int x = 0; x < dados; x++){
                    int dado = aleatorio.nextInt(max - min + 1) + min;
                    resultado = dado < resultado ? dado : resultado;
                }
            }   

            //Atualiza o resultado com modificações
            resultado = resultado + mods;
            mensagem = nome + " obteve  { " + resultado + " }  em um teste de " + teste;
        
        } else {
            mensagem = nome + " não pode realizar testes no estado de Enloquecido";
        } 
        
        saida.println(mensagem);
    }
}

class Acoes {

    //Personagem
    private Personagem personagem;

    //Mensagens do console
    private String mensagensConsole = "";

    //Campos
    private JLabel lnome;
    private JTextField fnome;
    private JLabel lestado;
    private JComboBox<String> festado;
    private JTextField ffor;
    private JTextField fint;
    private JTextField fdes;
    private JTextField fmis;
    private JTextField fcar;
    private JTextField fpro;
    private JLabel lfor;
    private JLabel lint;
    private JLabel ldes;
    private JLabel lmis;
    private JLabel lcar;
    private JLabel lpro;
    private JButton bsalvar;
    private JComboBox<String> fteste;
    private JButton bteste;
    private JTextArea fconsole;
    private JScrollPane fscrollpanel;
    private JTextField fmensagem;
    private JButton bmensagem;
    private JButton blimpar;

    //Estados
    private String estados [] = {
        "Normal", "Esgotado", "Assustado",
        "Apavorado", "Enlouquecido", "Desprevenido", 
        "Cansado", "Exausto", "Enjoado", 
    };

    //Testes
    private String testes [] = {
        "Combate", "Resistência", "Intimidação",
        "Magia", "Intelecto", "Percepção",
        "Agilidade", "Furtividade", "Mira",
        "Fé", "Encantamento", "Sanidade",
        "Persuasão", "Arte", "Sorte",
        "Sobrevivência", "Cutelaria", "Ciências",
    };

    //Ações
    public Acoes(Personagem personagem) {
        this.personagem = personagem;
        // Gui();
    }

    //Começa a aplicação
    public void Gui() {

        //Definindo Tela
        JFrame f = new JFrame("RPG");
        f.setVisible(true);
        f.setSize(435, 600);
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
        lnome = new JLabel("Nome");
        lnome.setFont(new Font("Arial", Font.PLAIN, fontSize));
        lnome.setSize(labelSize, labelHeight);
        lnome.setLocation(initialX, initialY);
        p.add(lnome);
 
        fnome = new JTextField();
        fnome.setSize(fieldSize +30, fieldHeight);
        fnome.setLocation(initialX + labelSize + space, initialY);
        p.add(fnome);


        //Estado
        lestado = new JLabel("Estado");
        lestado.setFont(new Font("Arial", Font.PLAIN, fontSize));
        lestado.setSize(labelSize, labelHeight);
        lestado.setLocation(250, initialY);
        p.add(lestado);
 
        festado = new JComboBox<String>(estados);
        festado.setSize(fieldSize, fieldHeight);
        festado.setLocation(250 + labelSize + space, initialY);
        p.add(festado);


        //Atributos
        ffor = new JTextField();
        fint = new JTextField();
        fdes = new JTextField();
        fmis = new JTextField();
        fcar = new JTextField();
        fpro = new JTextField();
        lfor = new JLabel("FOR");
        lint = new JLabel("INT");
        ldes = new JLabel("DES");
        lmis = new JLabel("MIS");
        lcar = new JLabel("CAR");
        lpro = new JLabel("PRO");
        
        int posX = 80;
        int posY = 100;
        int cont = 0;
        for(JTextField a: new JTextField [] {ffor, fint, fdes, fmis, fcar, fpro} ){
            if(cont == 3){
                posX = 80;
                posY += 80;
            }
            a.setSize(50, fieldHeight +10);
            a.setLocation(posX, posY);
            p.add(a);
            posX += 100;
            cont++;
        }
    
        posX = 95;
        posY = 130;
        cont = 0;
        for(JLabel a: new JLabel [] {lfor, lint, ldes, lmis, lcar, lpro} ){
            if(cont == 3){
                posX = 95;
                posY += 80;
            }
            a.setFont(new Font("Arial", Font.PLAIN, fontSize));
            a.setSize(50, fieldHeight +10);
            a.setLocation(posX, posY);
            p.add(a);
            posX += 100;
            cont++;
        }


        //Botão de Salvar
        bsalvar = new JButton("Salvar");
        bsalvar.setSize(80, 20);
        bsalvar.setLocation(20, 320);
        p.add(bsalvar);
        
        bsalvar.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                atualizarForm();
            }         
        });


        //Botão de Teste
        fteste = new JComboBox<String>(testes);
        fteste.setSize(fieldSize, fieldHeight);
        fteste.setLocation(175, 320);
        p.add(fteste);

        bteste = new JButton("Realizar Teste");
        bteste.setSize(120, 20);
        bteste.setLocation(275, 320);
        p.add(bteste);

        bteste.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                atualizarForm();
                personagem.teste(fteste.getSelectedItem().toString());
            }         
        });


        //Console de Mensagens
        fconsole = new JTextArea();
        fconsole.setEditable(false);
        fscrollpanel = new JScrollPane(fconsole);
        fscrollpanel.setSize(375, 150);
        fscrollpanel.setLocation(initialX, 350);
        p.add(fscrollpanel);


        //Escrever Mensagem
        fmensagem = new JTextField();
        fmensagem.setSize(fieldSize +160, fieldHeight +1);
        fmensagem.setLocation(initialX, 500);
        p.add(fmensagem);

        bmensagem = new JButton(">");
        bmensagem.setSize(fieldHeight +30, fieldHeight);
        bmensagem.setLocation(initialX +fieldSize +160, 500);
        p.add(bmensagem);


        //Limpar Console
        blimpar = new JButton("X");
        blimpar.setSize(fieldHeight +30, fieldHeight);
        blimpar.setLocation(initialX +fieldSize +224, 500);
        p.add(blimpar);

        blimpar.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                limparConsole();
            }         
        });


        //Adiciona o painel a tela
        f.add(p); 
    }

    public void atualizarForm(){
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
                fnome.getText().length() == 0 ? "Personagem" : fnome.getText(), 
                festado.getSelectedItem().toString(),
                traducoes[0],
                traducoes[1],
                traducoes[2],
                traducoes[3],
                traducoes[4],
                traducoes[5]
            );

            fnome.setText(fnome.getText().length() == 0 ? "Personagem" : fnome.getText());
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

    public void salvarPersonagem(String nome, String estado, int f, int i, int d, int m, int c, int p){
        personagem.nome = nome;
        personagem.estado = estado;
        personagem.f = f;
        personagem.i = i;
        personagem.d = d;
        personagem.m = m;
        personagem.c = c;
        personagem.p = p;
    }

    public void atualizarConsole(String mensagem){
        this.mensagensConsole = this.mensagensConsole + mensagem + "\n";
        this.fconsole.setText(this.mensagensConsole);
    }

    public void limparConsole(){
        this.mensagensConsole = "";
        this.fconsole.setText("");
    }

}

class Recebedor implements Runnable {

    private InputStream servidor;
    private Acoes acoes;

    public Recebedor(InputStream servidor, Acoes acoes) {
        this.servidor = servidor;
        this.acoes = acoes;
    }

    public void run() {

        // Instancia um scanner para mensagens vindas do servidor
        Scanner s = new Scanner(this.servidor);

        // Exibe todas as mensagens vindas do servidor
        while (s.hasNextLine()) {
            String retorno = s.nextLine();
            acoes.atualizarConsole(retorno);
            System.out.println(retorno);
        }

        s.close();
    }

}


public class Cliente {

    private static String host = "127.0.0.1";
    private static int porta = 12345;

    // private static Acoes acoes;

    public static void executa() throws UnknownHostException, IOException {

        // Cria um novo cliente
        Socket cliente = new Socket(host, porta);

        //Lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        //Cria um novo personagem e instancia suas acoes
        Personagem personagem = new Personagem(saida);
        Acoes acoes = new Acoes(personagem);
        acoes.Gui();

        //Cria uma thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream(), acoes);
        new Thread(r).start();

        while (teclado.hasNextLine()) {
            saida.println(personagem.nome + ": " + teclado.nextLine());
        }

        saida.close();
        teclado.close();
        cliente.close();
    }

    

    public static void main(String[] args) throws UnknownHostException, IOException {
        executa();
    }
}