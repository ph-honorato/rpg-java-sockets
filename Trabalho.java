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


class historia {

    public void escolherPersonagem(){
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
            "Ataque: 1xD8\tAtaque: 1xD10\tAtaque: 1xD6\tAtaque: 1xD6\t",
            "Ação: Mirar\tAção: Fúria\tAção: Defender\tAção: Curar",
            "",
            "",
            "",
        };

        for (String l: linhas){System.out.println(l);}
    }

    public void personagemEscolhido(String p){
        String [] linhas = {
            "",
            "Você escolheu a classe: " + p,
            "",
        };

        for (String l: linhas){System.out.println(l);}
    }

}


public class Trabalho {

    //scanner
    public static Scanner scanner = new Scanner(System.in);
    
    //historia
    public static historia h = new historia();

    //personagem
    public static personagem p;

    public static personagem escolherPersonagem(){
        
        personagem [] p = {
            new personagem("Arqueiro", 20, 20, 8, "mirar"),
            new personagem("Bárbaro", 25, 25, 10, "furia"),
            new personagem("Cavaleiro", 30, 30, 6, "defender"),
            new personagem("Mago", 15, 15, 6, "mago"),
        };

        System.out.print("Digite sua opção de classe: ");
        int opcao = scanner.nextInt();
        
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
            opcao = scanner.nextInt();
        }
        
        return null;
    }

    public static void main(String[] args){

        h.escolherPersonagem();
        
        p = escolherPersonagem();
        h.personagemEscolhido(p.classe);

    }

}
