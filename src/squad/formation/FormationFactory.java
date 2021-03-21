package squad.formation;

import player.Position;

import java.util.ArrayList;

public class FormationFactory {

    public Formation getFormation(String formationName) {
        if (formationName.equals("41212")) {
            return new F_41212();
        } else if (formationName.equals("4231")) {
//            return new Squad.Formation.F_4231
        }

        return null;
    }
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
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

//Matt changes begin here

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
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

class F_343 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_343() {
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position ccb = new Position("CB", "CCB");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position rw = new Position("RW");
        Position lw = new Position("LW");
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
        positions.add(rw);
        positions.add(lw);
        positions.add(st);

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
        graph.addEdge(lm, lw);
        graph.addEdge(rm, rcm);
        graph.addEdge(rm, rw);
        graph.addEdge(lcm, rcm);
        graph.addEdge(lcm, st);
        graph.addEdge(rcm, st);
        graph.addEdge(rw, st);
        graph.addEdge(lw, st);
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

class F_352 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_352() {
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position ccb = new Position("CB", "CCB");
        Position lcdm = new Position("CDM", "LCDM");
        Position rcdm = new Position("CDM", "RCDM");
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
        positions.add(lcdm);
        positions.add(rcdm);
        positions.add(rm);
        positions.add(lm);
        positions.add(cam);
        positions.add(lst);
        positions.add(rst);

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rm);
        graph.addEdge(rcb, ccb);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, rcdm);
        graph.addEdge(lcb, lm);
        graph.addEdge(lcb, ccb);
        graph.addEdge(lcb, gk);
        graph.addEdge(lcb, lcdm);
        graph.addEdge(ccb, gk);
        graph.addEdge(ccb, lcdm);
        graph.addEdge(ccb, rcdm);
        graph.addEdge(lm, lcdm);
        graph.addEdge(lm, lst);
        graph.addEdge(rm, rcdm);
        graph.addEdge(rm, rst);
        graph.addEdge(lcdm, rcdm);
        graph.addEdge(lcdm, cam);
        graph.addEdge(rcdm, cam);
        graph.addEdge(cam, lst);
        graph.addEdge(cam, rst);
        graph.addEdge(lst, rst);
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

class F_412122 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_412122() {
        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
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
        positions.add(rcm);
        positions.add(cdm);
        positions.add(lcm);
        positions.add(lst);
        positions.add(rst);
        positions.add(cam);

        graph = new Graph(this.positions);
        graph.addEdge(rb, rcm);
        graph.addEdge(rb, rcb);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, cdm);
        graph.addEdge(rcb, lcb);
        graph.addEdge(lcb, gk);
        graph.addEdge(lcb, lb);
        graph.addEdge(lcb, cdm);
        graph.addEdge(lb, lcm);
        graph.addEdge(rcm, cdm);
        graph.addEdge(rcm, cam);
        graph.addEdge(rcm, rst);
        graph.addEdge(lcm, lst);
        graph.addEdge(lcm, cam);
        graph.addEdge(lcm, cdm);
        graph.addEdge(cdm, cam);
        graph.addEdge(cam, lst);
        graph.addEdge(cam, rst);
        graph.addEdge(lst, rst);
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

class F_4141 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_4141() {
        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position cdm = new Position("CDM");
        Position lcm = new Position("CM", "LCM");
        Position rcm = new Position("CM", "RCM");
        Position rm = new Position("RM");
        Position lm = new Position("LM");
        Position st = new Position("ST");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rcm);
        positions.add(cdm);
        positions.add(lcm);
        positions.add(lm);
        positions.add(rm);
        positions.add(st);

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rcm);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, cdm);
        graph.addEdge(rcb, rb);
        graph.addEdge(rcb, lcb);
        graph.addEdge(lcb, lcm);
        graph.addEdge(lcb, gk);
        graph.addEdge(lcb, cdm);
        graph.addEdge(lcb, lb);
        graph.addEdge(lb, lm);
        graph.addEdge(rb, rm);
        graph.addEdge(cdm, lcm);
        graph.addEdge(cdm, rcm);
        graph.addEdge(lcm, lm);
        graph.addEdge(lcm, st);
        graph.addEdge(lcm, rcm);
        graph.addEdge(rcm, rm);
        graph.addEdge(rcm, st);
        graph.addEdge(lm, st);
        graph.addEdge(rm, st);
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}

class F_4231 implements Formation {

    private Graph graph;
    private ArrayList<Position> positions;

    public F_4231() {
        Position rb = new Position("RB");
        Position lb = new Position("LB");
        Position gk = new Position("GK");
        Position lcb = new Position("CB", "LCB");
        Position rcb = new Position("CB", "RCB");
        Position lcdm = new Position("CDM", "LCDM");
        Position rcdm = new Position("CDM", "RCDM");
        Position ccam = new Position("CAM", "CCAM");
        Position lcam = new Position("CAM", "LCAM");
        Position rcam = new Position("CAM", "RCAM");
        Position st = new Position("ST");

        positions = new ArrayList<>();
        positions.add(rb);
        positions.add(lb);
        positions.add(gk);
        positions.add(lcb);
        positions.add(rcb);
        positions.add(rcdm);
        positions.add(lcdm);
        positions.add(ccam);
        positions.add(lcam);
        positions.add(rcam);
        positions.add(st);

        graph = new Graph(this.positions);
        graph.addEdge(rcb, rb);
        graph.addEdge(rcb, rcdm);
        graph.addEdge(rcb, gk);
        graph.addEdge(rcb, lcb);
        graph.addEdge(lcb, lb);
        graph.addEdge(lcb, lcdm);
        graph.addEdge(lcb, gk);
        graph.addEdge(lb, lcdm);
        graph.addEdge(rb, rcdm);
        graph.addEdge(lcdm, ccam);
        graph.addEdge(lcdm, lcam);
        graph.addEdge(rcdm, ccam);
        graph.addEdge(rcdm, rcam);
        graph.addEdge(ccam, rcam);
        graph.addEdge(ccam, lcam);
        graph.addEdge(ccam, st);
        graph.addEdge(lcam, st);
        graph.addEdge(rcam, st);
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }

    public Graph getGraph() {
        return this.graph;
    }
}