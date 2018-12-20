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

        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position cam = new Position("CAM");
        Position lst = new Position("ST", "LST");
        Position rst = new Position("ST", "RST");
        Position cdm = new Position("CDM");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rm);
        positions.add(cdm);
        positions.add(lm);
        positions.add(lst);
        positions.add(rst);
        positions.add(cam);

        this.positions = positions;

        graph = new Graph(this.positions);
        graph.addEdge(rb, rm);
        graph.addEdge(rb, rcb);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, cdm);
        graph.addEdge(rcb, lcb);
        graph.addEdge(lcb, gk);
        graph.addEdge(lcb, lb);
        graph.addEdge(lcb, cdm);
        graph.addEdge(lb, lm);
        graph.addEdge(rm, cdm);
        graph.addEdge(rm, cam);
        graph.addEdge(rm, rst);
        graph.addEdge(lm, lst);
        graph.addEdge(lm, cam);
        graph.addEdge(lm, cdm);
        graph.addEdge(cdm, cam);
        graph.addEdge(cam, lst);
        graph.addEdge(cam, rst);
        graph.addEdge(lst, rst);

        this.graph = graph;

    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }

}
