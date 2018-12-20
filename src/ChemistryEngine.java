import java.util.ArrayList;
import java.util.HashMap;

public class ChemistryEngine {

    private Squad squad;

    public static int calculateChemistry(Squad squad) {

        //check links for each position and add

        squad.printSquad();
        for(HashMap.Entry<Position, Player> entry : squad.getLineup().entrySet()) {

            Position pos = entry.getKey();
            Player player = entry.getValue();

            Position cur_pos = new Position(player.getPos());
            int init_positional_chem = positionChem(pos, cur_pos);

            int links = 0;

            ArrayList<Position> neighbors = squad.getGraph().getAdjList().get(pos);

            for (Position neighbor : neighbors) {
                System.out.println("pos: " + pos.getPos() + " links to " + neighbor.getPos());
                Player neighbor_player = squad.getLineup().get(neighbor);
                links += numSharedLinks(player, neighbor_player);
            }

            System.out.println("player: " + player.getName() + " has " + links + "/" + neighbors.size());

        }



        return 0;
    }

    public static int numSharedLinks(Player p1, Player p2) {
        int links = 0;

        if (p1.getNation().equals(p2.getName())) {
            links += 1;
        }
        if (p1.getLeague().equals(p2.getLeague())) {
            links += 1;
        }
        if (p1.getTeam().equals(p2.getTeam())) {
            links += 1;
        }

        return links;
    }

    public static int positionChem(Position slot, Position actual) {

        if (slot.getPos().equals("GK")) {
            if (actual.getPos().equals("GK")) {
              return 3;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("CB")) {
            if (actual.getPos().equals("CB")) {
                return 3;
            } else if (actual.getPos().equals("LB") || actual.getPos().equals("RB")) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("RB")) {
            if (actual.getPos().equals("RB")) {
                return 3;
            } else if (actual.getPos().equals("CB") || actual.getPos().equals("LB") || actual.getPos().equals("RM")) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("LB")) {
            if (actual.getPos().equals("LB")) {
                return 3;
            } else if (actual.getPos().equals("CB") || actual.getPos().equals("RB") || actual.getPos().equals("LM")) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("CDM")) {
            if (actual.getPos().equals("CDM")) {
                return 3;
            } else if (actual.getPos().equals("CB") || actual.getPos().equals("CAM")) {
                return 1;
            } else if (actual.getPos().equals("CM")) {
                return 2;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("CAM")) {
            if (actual.getPos().equals("CAM")) {
                return 3;
            } else if (actual.getPos().equals("CF") || actual.getPos().equals("CM")) {
                return 2;
            } else if (actual.getPos().equals("CDM")) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("CM")) {
            if (actual.getPos().equals("CM")) {
                return 3;
            } else if (actual.getPos().equals("CAM") || actual.getPos().equals("CDM")) {
                return 2;
            } else if (actual.getPos().equals("LM") || actual.getPos().equals("RM")) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("CF")) {
            if (actual.getPos().equals("ST") || actual.getPos().equals("CAM")) {
                return 2;
            } else if (actual.getPos().equals("CF")) {
                return 3;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("ST")) {
            if (actual.getPos().equals("ST")) {
                return 3;
            } else if (actual.getPos().equals("CF")) {
                return 2;
            } else {
                return 0;
            }
        } else if (slot.getPos().equals("RM")) {
            if (actual.getPos().equals("RM")) {
                return 3;
            } else if (actual.getPos().equals("RW")) {
                return 2;
            } else if (actual.getPos().equals("RF") || actual.getPos().equals("RB")) {
                return 1;
            }
        } else if (slot.getPos().equals("LM")) {
            if (actual.getPos().equals("LM")) {
                return 3;
            } else if (actual.getPos().equals("LW")) {
                return 2;
            } else if (actual.getPos().equals("LF") || actual.getPos().equals("LB")) {
                return 1;
            }
        } else if (slot.getPos().equals("LW")) {
            if (actual.getPos().equals("LW")) {
                return 3;
            } else if (actual.getPos().equals("LM") || actual.getPos().equals("LF")) {
                return 2;
            } else if (actual.getPos().equals("LWB")) {
                return 1;
            }
        } else if (slot.getPos().equals("RW")) {
            if (actual.getPos().equals("RW")) {
                return 3;
            } else if (actual.getPos().equals("RM") || actual.getPos().equals("RF")) {
                return 2;
            } else if (actual.getPos().equals("RWB")) {
                return 1;
            }
        } else if (slot.getPos().equals("LF")) {
            if (actual.getPos().equals("LF")) {
                return 3;
            } else if (actual.getPos().equals("LW")) {
                return 2;
            } else if (actual.getPos().equals("LM") || actual.getPos().equals("ST")) {
                return 1;
            }
        } else if (slot.getPos().equals("RF")) {
            if (actual.getPos().equals("RF")) {
                return 3;
            } else if (actual.getPos().equals("RW")) {
                return 2;
            } else if (actual.getPos().equals("RM") || actual.getPos().equals("ST")) {
                return 1;
            }
        } else if (slot.getPos().equals("RWB")) {
            if (actual.getPos().equals("RWB")) {
                return 3;
            } else if (actual.getPos().equals("RB")) {
                return 2;
            } else if (actual.getPos().equals("RW") || actual.getPos().equals("RM")) {
                return 1;
            }
        } else if (slot.getPos().equals("LWB")) {
            if (actual.getPos().equals("LWB")) {
                return 3;
            } else if (actual.getPos().equals("LB")) {
                return 2;
            } else if (actual.getPos().equals("LW") || actual.getPos().equals("LM")) {
                return 1;
            }
        } else {
            return 0;
        }

        return 0;

    }


}
