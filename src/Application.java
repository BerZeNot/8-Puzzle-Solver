import model.Action;
import model.Node;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import static java.lang.System.exit;

public class Application {
    public static void main(String[] args) {

        int estadoInicial[][] = {{2,3,6},{1,5,8},{4,7,0}};
        int estadoObjetivo[][] = {{1,2,3},{4,5,6},{7,8,0}};

        Queue borda = new PriorityQueue<Node>();
        List explorados = new ArrayList<Node>();

        borda.add(criaNo(estadoInicial));

        do {
            if(borda.size() > 0){
                Node no = (Node) borda.remove();
                if(no.estadoEhIgualAoObjetivo(estadoObjetivo)){
                    System.out.println("Apresentando solução...");
                    do{
                        System.out.println(no.getAction());
                        no = no.getPai();
                    } while (no.getPai()!=null);

                    exit(0);
                }

                explorados.add(no);

                List<Node> sucessores = expande(no);
                for(Node n: sucessores){
                    if(!estadoFoiExplorado(explorados, n)){
                        borda.add(n);
                    }
                }
            }
        } while (borda.size()>0 );

        System.out.println("Não tem solução para o estado atual! :'(");
    }

    public static Node criaNo(int[][] estadoInicial){
        int xBranco=0, yBranco=0;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(estadoInicial[i][j] == 0){
                    xBranco = i;
                    yBranco = j;
                    break;
                }

        return new Node(estadoInicial,null, 8,0,xBranco,yBranco,null);
    }

    public static boolean estadoFoiExplorado(List<Node> explorados, Node no){
        for(Node n: explorados){
            if(n.temEstadoIgualAoDe(no))
                return true;
        }
        return false;
    }

    public static ArrayList<Node> expande(Node no){
        ArrayList<Node> nosExpandidos = new ArrayList<>();

        // Se for possível movimentar para cima, crie um no com movimento à cima;
        if(no.getxBranco() > 0){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para cima"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()-1][no.getyBranco()];
            novoEstadoC[no.getxBranco()-1][no.getyBranco()] = 0;
            nosExpandidos.add(new Node(novoEstadoC,
                    no,
                    8,
                    no.getG()+1,
                    no.getxBranco()-1,
                    no.getyBranco(),
                    Action.CIMA)
            );
        }
        // Se for possível movimentar para direita, crie um no com movimento à direita;
        if(no.getyBranco() < 2){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para direita"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()][no.getyBranco()+1];
            novoEstadoC[no.getxBranco()][no.getyBranco()+1] = 0;
            nosExpandidos.add(new Node(novoEstadoC,
                    no,
                    8,
                    no.getG()+1,
                    no.getxBranco(),
                    no.getyBranco()+1,
                    Action.DIREITA)
            );
        }
        // Se for possível movimentar para baixo, crie um no com movimento à baixo;
        if(no.getxBranco() < 2){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para baixo"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()+1][no.getyBranco()];
            novoEstadoC[no.getxBranco()+1][no.getyBranco()] = 0;
            nosExpandidos.add(new Node(novoEstadoC,
                    no,
                    8,
                    no.getG()+1,
                    no.getxBranco()+1,
                    no.getyBranco(),
                    Action.BAIXO)
            );
        }
        // Se for possível movimentar para esquerda, crie um no com movimento à esquerda;
        if(no.getyBranco() > 0){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para direita"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()][no.getyBranco()-1];
            novoEstadoC[no.getxBranco()][no.getyBranco()-1] = 0;
            nosExpandidos.add(new Node(novoEstadoC,
                    no,
                    8,
                    no.getG()+1,
                    no.getxBranco(),
                    no.getyBranco()-1,
                    Action.ESQUERDA)
            );
        }
        return nosExpandidos;
    }
}
