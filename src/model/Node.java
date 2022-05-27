package model;

public class Node {
    private int[][] estado;
    private Node pai;
    private int h;
    private int g;
    private int f;
    private int xBranco;
    private int yBranco;

    public Node(int[][] estado, Node pai, int h, int g, int xBranco, int yBranco) {
        this.estado = estado;
        this.pai = pai;
        this.h = h;
        this.g = g;
        this.xBranco = xBranco;
        this.yBranco = yBranco;
        f = h+g;
    }

    public int[][] getEstado() {
        return estado;
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
    }

    public int getG() {
        return g;
    }

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Node:{\n");
        sb.append("\tpai: " + pai + ",\n");
        sb.append("\th:" + h + ",\n");
        sb.append("\tg:" + g + ",\n");
        sb.append("\tf:" + f + ",\n");
        sb.append("\txBranco:" + xBranco + ",\n");
        sb.append("\tyBranco:" + yBranco + "\n");
        sb.append("}\n");

        return sb.toString();
    }
    public String printEstado(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%d][%d][%d]\n",estado[0][0],estado[0][1],estado[0][2]));
        sb.append(String.format("[%d][%d][%d]\n",estado[1][0],estado[1][1],estado[1][2]));
        sb.append(String.format("[%d][%d][%d]\n",estado[2][0],estado[2][1],estado[2][2]));
        return sb.toString();
    }
}
