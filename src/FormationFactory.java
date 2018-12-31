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

class F_3412 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_3412() {

        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position ccb = new Position("CB", "CCB");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position cam = new Position("CAM");
        Position lst = new Position("ST", "LST");
        Position rst = new Position("ST", "RST");

        positions = new ArrayList<>();
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(ccb);
        positions.add(lcm);
        positions.add(rcm);
        positions.add(rm);
        positions.add(lm);
        positions.add(cam);
        positions.add(lst);
        positions.add(rst);

        this.positions = positions;

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rm);
        graph.addEdge(lcb, lm);
        graph.addEdge(ccb, lcb);
        graph.addEdge(ccb, rcb);
        graph.addEdge(ccb, gk);
        graph.addEdge(rcb, gk);
        graph.addEdge(lcb, gk);
        graph.addEdge(lm, lcm);
        graph.addEdge(lm, lst);
        graph.addEdge(rm, rcm);
        graph.addEdge(rm, rst);
        graph.addEdge(rcm, cam);
        graph.addEdge(rcm, lcm);
        graph.addEdge(rcm, ccb);
        graph.addEdge(lcm, cam);
        graph.addEdge(lcm, ccb);
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

class F_3421 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_3421() {

        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position ccb = new Position("CB", "CCB");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position lf = new Position("LF");
        Position rf = new Position("RF");
        Position st = new Position("ST");

        positions = new ArrayList<>();
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(ccb);
        positions.add(lcm);
        positions.add(rcm);
        positions.add(rm);
        positions.add(lm);
        positions.add(lf);
        positions.add(rf);
        positions.add(st);

        this.positions = positions;

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rm);
        graph.addEdge(rcb, ccb);
        graph.addEdge(rcb, gk);
        graph.addEdge(lcb, lm);
        graph.addEdge(lcb, ccb);
        graph.addEdge(lcb, gk);
        graph.addEdge(ccb, gk);
        graph.addEdge(ccb, lcm);
        graph.addEdge(ccb, rcm);
        graph.addEdge(lm, lcm);
        graph.addEdge(lm, lf);
        graph.addEdge(rm, rcm);
        graph.addEdge(rm, rf);
        graph.addEdge(lcm, rcm);
        graph.addEdge(lcm, lf);
        graph.addEdge(rcm, lcm);
        graph.addEdge(rcm, rf);
        graph.addEdge(rf, st);
        graph.addEdge(lf, st);

        this.graph = graph;

    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }

}

class F_3142 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_3142() {

        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position ccb = new Position("CB", "CCB");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
        Position cdm = new Position("CDM");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position lst = new Position("ST", "LST");
        Position rst = new Position("ST", "RST");

        positions = new ArrayList<>();
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(ccb);
        positions.add(lcm);
        positions.add(rcm);
        positions.add(cdm);
        positions.add(rm);
        positions.add(lm);
        positions.add(lst);
        positions.add(rst);

        this.positions = positions;

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rm);
        graph.addEdge(rcb, ccb);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, cdm);
        graph.addEdge(lcb, lm);
        graph.addEdge(lcb, ccb);
        graph.addEdge(lcb, cdm);
        graph.addEdge(lcb, gk);
        graph.addEdge(ccb, gk);
        graph.addEdge(ccb, cdm);
        graph.addEdge(lm, lcm);
        graph.addEdge(lm, lst);
        graph.addEdge(rm, rcm);
        graph.addEdge(rm, rst);
        graph.addEdge(lcm, cdm);
        graph.addEdge(lcm, lst);
        graph.addEdge(rcm, cdm);
        graph.addEdge(rcm, rst);
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
