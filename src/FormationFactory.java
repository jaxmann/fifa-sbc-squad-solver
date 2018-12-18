import java.util.ArrayList;

public class FormationFactory {

    public Formation getFormation(String formationName) {
        if (formationName.equals("41212")) {
            return new F_41212();
        } else if (formationName.equals("4231")) {
//            return new F_4231
        }

        return null;
    }

}

interface Formation {
    ArrayList<Position> getPositions();
    Graph getGraph();
}

class F_41212 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_41212() {

        positions.add(Position.RB);
        positions.add(Position.LB);
        positions.add(Position.GK);
        positions.add(Position.LCB);
        positions.add(Position.RCB);
        positions.add(Position.RB);
        positions.add(Position.RM);
        positions.add(Position.LM);
        positions.add(Position.LST);
        positions.add(Position.RST);
        positions.add(Position.CAM);

        this.positions = positions;

        graph = new Graph(this.positions);
        graph.addEdge(Position.RB, Position.RM);
        graph.addEdge(Position.RB, Position.RCB);
        graph.addEdge(Position.RCB, Position.GK);
        graph.addEdge(Position.RCB, Position.CDM);
        graph.addEdge(Position.RCB, Position.LCB);
        graph.addEdge(Position.LCB, Position.GK);
        graph.addEdge(Position.LCB, Position.LB);
        graph.addEdge(Position.LCB, Position.CDM);
        graph.addEdge(Position.LB, Position.LM);
        graph.addEdge(Position.RM, Position.CDM);
        graph.addEdge(Position.RM, Position.CAM);
        graph.addEdge(Position.RM, Position.RST);
        graph.addEdge(Position.LM, Position.LST);
        graph.addEdge(Position.LM, Position.CAM);
        graph.addEdge(Position.LM, Position.CDM);
        graph.addEdge(Position.CDM, Position.CAM);
        graph.addEdge(Position.CAM, Position.LST);
        graph.addEdge(Position.CAM, Position.RST);
        graph.addEdge(Position.LST, Position.RST);

        this.graph = graph;

    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }

}
