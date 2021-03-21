package squad.formation;

import player.Position;

import java.util.ArrayList;

public interface Formation {
    ArrayList<Position> getPositions();
    Graph getGraph();
}