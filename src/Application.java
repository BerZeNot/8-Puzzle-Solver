import model.Node;

public class Application {
    public static void main(String[] args) {
        int estadoObjetivo[][] = {{1,2,3},{4,5,6},{7,8,0}};
        int estadoAtual[][] = {{1,2,3},{4,5,6},{7,8,0}};

        Node n1 = new Node(estadoObjetivo,null,8,10,2,2);
        System.out.println("Estado Atual:\n" + n1.printEstado());
        System.out.println(n1);
    }
}
