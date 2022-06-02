package model;

public class Node implements Comparable<Node>{
    private int[][] estado;
    private Node pai;
    private int h;
    private int g;
    private int f;
    private int xBranco;
    private int yBranco;
    private Action action;

    public Node(int[][] estado, Node pai, int h, int g, int xBranco, int yBranco, Action action) {
        this.estado = estado;
        this.pai = pai;
        this.h = h;
        this.g = g;
        this.xBranco = xBranco;
        this.yBranco = yBranco;
        this.action = action;
        f = h+g;
    }

    public boolean estadoEhIgualAoObjetivo(int[][] estadoObjetivo){
        boolean ehIgualAoObjetivo = true;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(this.estado[i][j] != estadoObjetivo[i][j]){
                    ehIgualAoObjetivo = false;
                    break;
                }
            }
        }
        return ehIgualAoObjetivo;
    }

    public boolean temEstadoIgualAoDe(Node no){
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(this.estado[i][j] != no.getEstado()[i][j])
                    return false;

        return true;
    }

    public int[][] getEstado() {
        return estado;
    }

    public int[][] getCopiaDoEstado(){
        int[][] tmpEstado = new int[3][3];
        for(int i=0; i<3;i++)
            for(int j=0; j<3;j++)
                tmpEstado[i][j] = this.estado[i][j];

        return tmpEstado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;

        // Atualiza F
        this.f = this.g + this.h;
    }

    public int getG() {
        return g;
    }

    public int getF() { return f; }

    public void setG(int g) {
        this.g = g;
    }

    public int getxBranco() {
        return xBranco;
    }

    public void setxBranco(int xBranco) {
        this.xBranco = xBranco;
    }

    public int getyBranco() {
        return yBranco;
    }

    public void setyBranco(int yBranco) {
        this.yBranco = yBranco;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node:{\n");
        sb.append("\tpai: " + pai + ",\n");
        sb.append("\th: " + h + ",\n");
        sb.append("\tg: " + g + ",\n");
        sb.append("\tf: " + f + ",\n");
        sb.append("\tação: " + action + ",\n");
        sb.append("\txBranco: " + xBranco + ",\n");
        sb.append("\tyBranco: " + yBranco + "\n");
        sb.append("}\n");

        return sb.toString();
    }

    public void printEstado(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%d][%d][%d]\n",estado[0][0],estado[0][1],estado[0][2]));
        sb.append(String.format("[%d][%d][%d]\n",estado[1][0],estado[1][1],estado[1][2]));
        sb.append(String.format("[%d][%d][%d]\n",estado[2][0],estado[2][1],estado[2][2]));
        System.out.println(sb.toString());
    }

    public static void printEstado(int estado[][], int ordemDaMatrizDeEstado){
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<ordemDaMatrizDeEstado; i++){
            for(int j=0; j<ordemDaMatrizDeEstado; j++){
                sb.append(String.format("[%d]",estado[i][j]));
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Override
    public int compareTo(Node o) {
        if(this.f == o.getF())
            return 0;
        else if(this.f > o.getF())
            return 1;
        else
            return -1;
    }
}
