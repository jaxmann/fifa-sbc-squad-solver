package squad.formation;

import player.position.Position;

import java.util.ArrayList;

public interface Formation {
    ArrayList<Position> getPositions();
    Graph getGraph();
}