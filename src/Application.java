import model.Action;
import model.Node;
import java.util.*;

import static java.lang.System.exit;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int estadoInicial[][] = new int[3][3];
        int estadoObjetivo[][] = new int[3][3];
        lerEstado(estadoInicial, 3, sc, "inicial");
        lerEstado(estadoObjetivo, 3, sc, "objetivo");
        int heuristica = lerHeuristica(sc);

        PriorityQueue borda = new PriorityQueue<Node>();
        List explorados = new ArrayList<Node>();

        borda.add(criaNo(estadoInicial, heuristica, estadoObjetivo));
        int count = 1;
        do {
            System.out.println("Passo: " + count);
            count++;
            if(borda.size() > 0){
                Node no = (Node) borda.remove();
                if(no.estadoEhIgualAoObjetivo(estadoObjetivo)){
                    System.out.println("-------------------------------------");
                    System.out.println("Apresentando solução...");
                    LinkedList<Node> result = new LinkedList<>();
                    int custoTotal = 0;
                    do{
                        result.add(no);
                        custoTotal += no.getF();
                        no = no.getPai();
                    } while (no.getPai()!=null);
                    Collections.reverse(result);
                    result.forEach(r -> {
                        System.out.println("Ação: " + r.getAction());
                        System.out.println("Estado: ");
                        r.printEstado();
                        System.out.println("h(n): " + r.getH());
                        System.out.println("g(n): " + r.getG());
                        System.out.println("f(n): " + r.getF());
                        System.out.println("-------------------------------------");
                    });
                    System.out.println("Custo total: " + custoTotal);
                    System.out.print("[ ");
                    result.forEach(n ->{
                        System.out.print(n.getAction() + " ");
                    });
                    System.out.print("]");
                    exit(0);
                }

                explorados.add(no);

                List<Node> sucessores = expande(no, heuristica, estadoObjetivo);
                for(Node n: sucessores){
                    if(!estadoFoiExplorado(explorados, n)){
                        Node possivelEstadoIdenticoNaBorda = getEstadoIdenticoNaBorda(n, borda);
                        if(possivelEstadoIdenticoNaBorda != null){
                            // Se houver um estado semelhante na borda
                            //    Se ele for pior que o novo estado, ele será removido e dará lugar ao novo.
                            if(n.getF() < possivelEstadoIdenticoNaBorda.getF()){
                                //System.out.printf("Trocou um node de F=%d por um com F=%d\n" + possivelEstadoIdenticoNaBorda.getF(), n.getF());
                                borda.remove(possivelEstadoIdenticoNaBorda);
                                borda.add(n);
                            }
                        } else{
                            borda.add(n);
                        }
                    }
                }
            }
        } while (borda.size()>0 );

        System.out.println("Não há solução para este estado inicial! :'(");
        Node.printEstado(estadoInicial, 3);
        sc.close();
    }

    public static Node criaNo(int[][] estadoInicial, int heuristica, int[][] estadoObjetivo){
        int xBranco=0, yBranco=0;
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(estadoInicial[i][j] == 0){
                    xBranco = i;
                    yBranco = j;
                    break;
                }
        Node inicial = new Node(estadoInicial,null, 8,0,xBranco,yBranco,null);
        if(heuristica == 1) // 1 = número de blocos em posição errada
            calculaNumeroDeBlocosEmPosicaoErrada(inicial, estadoObjetivo,3);
        else if(heuristica == 2){ // distância de Manhattan
            calculaDistanciaDeManhattan(inicial, estadoObjetivo, 3);
        }
        return inicial;
    }

    public static boolean estadoFoiExplorado(List<Node> explorados, Node no){
        for(Node n: explorados){
            if(n.temEstadoIgualAoDe(no))
                return true;
        }
        return false;
    }

    public static Node getEstadoIdenticoNaBorda(Node node, PriorityQueue<Node> borda){
        for(Node nodeNaBorda: borda){
            if(node.temEstadoIgualAoDe(nodeNaBorda))
                return nodeNaBorda;
        }
        return null;
    }

    public static ArrayList<Node> expande(Node no, int heuristica, int[][] estadoObjetivo){
        ArrayList<Node> nosExpandidos = new ArrayList<>();

        // Se for possível movimentar para cima, crie um no com movimento à cima;
        if(no.getxBranco() > 0){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para cima"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()-1][no.getyBranco()];
            novoEstadoC[no.getxBranco()-1][no.getyBranco()] = 0;
            Node nTemp1 = new Node(novoEstadoC,
                    no,
                    0,
                    no.getG()+1,
                    no.getxBranco()-1,
                    no.getyBranco(),
                    Action.CIMA);
            if(heuristica == 1) {
                calculaNumeroDeBlocosEmPosicaoErrada(nTemp1, estadoObjetivo, 3);
            }
            else if(heuristica == 2){
                calculaDistanciaDeManhattan(nTemp1, estadoObjetivo,3);
            }

            nosExpandidos.add(nTemp1);
        }
        // Se for possível movimentar para direita, crie um no com movimento à direita;
        if(no.getyBranco() < 2){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para direita"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()][no.getyBranco()+1];
            novoEstadoC[no.getxBranco()][no.getyBranco()+1] = 0;
            Node nTemp2 = new Node(novoEstadoC,
                    no,
                    0,
                    no.getG()+1,
                    no.getxBranco(),
                    no.getyBranco()+1,
                    Action.DIREITA);
            if(heuristica == 1)
                calculaNumeroDeBlocosEmPosicaoErrada(nTemp2, estadoObjetivo,3);
            else if(heuristica == 2)
                calculaDistanciaDeManhattan(nTemp2, estadoObjetivo,3);

            nosExpandidos.add(nTemp2);
        }
        // Se for possível movimentar para baixo, crie um no com movimento à baixo;
        if(no.getxBranco() < 2){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para baixo"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()+1][no.getyBranco()];
            novoEstadoC[no.getxBranco()+1][no.getyBranco()] = 0;
            Node nTemp3 = new Node(novoEstadoC,
                    no,
                    8,
                    no.getG()+1,
                    no.getxBranco()+1,
                    no.getyBranco(),
                    Action.BAIXO);

            if(heuristica == 1)
                calculaNumeroDeBlocosEmPosicaoErrada(nTemp3, estadoObjetivo,3);
            else if(heuristica == 2)
                calculaDistanciaDeManhattan(nTemp3, estadoObjetivo,3);

            nosExpandidos.add(nTemp3);
        }
        // Se for possível movimentar para esquerda, crie um no com movimento à esquerda;
        if(no.getyBranco() > 0){
            // Copia o estado do pai
            int[][] novoEstadoC = no.getCopiaDoEstado();
            // Executa a ação "mover para direita"
            novoEstadoC[no.getxBranco()][no.getyBranco()] = novoEstadoC[no.getxBranco()][no.getyBranco()-1];
            novoEstadoC[no.getxBranco()][no.getyBranco()-1] = 0;
            Node nTemp4 = new Node(novoEstadoC,
                    no,
                    0,
                    no.getG()+1,
                    no.getxBranco(),
                    no.getyBranco()-1,
                    Action.ESQUERDA);
            if(heuristica == 1)
                calculaNumeroDeBlocosEmPosicaoErrada(nTemp4, estadoObjetivo,3);
            else if(heuristica == 2)
                calculaDistanciaDeManhattan(nTemp4, estadoObjetivo,3);

            nosExpandidos.add(nTemp4);
        }
        return nosExpandidos;
    }

    public static void lerEstado(int[][] estado, int ordemDaMatriz, Scanner sc, String tipoDoEstado){
        System.out.println("Informe os 9 valores da para a matriz de estado " + tipoDoEstado + ":");
        for(int i=0; i<ordemDaMatriz; i++){
            for(int j=0; j<ordemDaMatriz; j++){
                estado[i][j] = sc.nextInt();
            }
        }
    }

    public static int lerHeuristica(Scanner sc){
        int heuristica;
        do{
            System.out.println("Qual heuristica deseja utilizar?");
            System.out.println("1 - Número de blocos em posições erradas");
            System.out.println("2 - Distância de Manhattan");
            heuristica = sc.nextInt();
        } while (heuristica!= 1 && heuristica != 2);
        return heuristica;
    }

    public static void calculaDistanciaDeManhattan(Node node, int[][] estadoObjetivo, int ordemDaMatriz){
        int distanciaTotal = 0;
        int[][] estadoAtual = node.getEstado();
        for(int i=0; i<ordemDaMatriz; i++) {
            for (int j = 0; j < ordemDaMatriz; j++) {
                if(estadoAtual[i][j] != estadoObjetivo[i][j]){
                    for(int k=0; k<ordemDaMatriz; k++){
                        for(int l=0; l<ordemDaMatriz; l++){
                            if(estadoObjetivo[k][l] == estadoAtual[i][j]){
                                int distanciaParcial = Math.abs(k-i + l-j);
                                distanciaTotal += distanciaParcial;
                                break;
                            }
                        }
                    }
                }
            }
        }
        node.setH(distanciaTotal);
    }

    public static void calculaNumeroDeBlocosEmPosicaoErrada(Node node, int[][] estadoObjetivo, int ordemDaMatriz){
        int distanciaTotal = 0;
        int[][] nodeState = node.getEstado();
        for(int i=0; i<ordemDaMatriz; i++) {
            for (int j = 0; j < ordemDaMatriz; j++) {
                if(nodeState[i][j] != estadoObjetivo[i][j]){
                    distanciaTotal++;
                }
            }
        }
        node.setH(distanciaTotal);
    }
}
