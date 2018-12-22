import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph implements Serializable {

    private HashMap<Position, ArrayList<Position>> adjList;
    private ArrayList<Position> positions;

    public Graph(ArrayList<Position> positions) {

        this.positions = positions;
        this.adjList = new HashMap<>();

        for (Position p : positions) {
            adjList.put(p, new ArrayList<Position>());
        }
    }

    public Graph deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Graph) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addEdge(Position src, Position dest) {
        this.adjList.get(src).add(dest);
        this.adjList.get(dest).add(src);
    }

    public HashMap<Position, ArrayList<Position>> getAdjList() {
        return this.adjList;
    }

    public void printGraph(Graph graph) {
        for (Position p : this.positions) {
            System.out.println("Adjacency list of vertex " + p);
            System.out.print("head");
            for (Object cur : this.adjList.get(p)) {
                System.out.print(" -> " + cur);
            }
            System.out.println("\n");
        }
    }
}







