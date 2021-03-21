package chemistry;

import player.BasePosition;
import player.Player;
import player.Position;
import squad.Squad;

import java.util.ArrayList;
import java.util.HashMap;

public class ChemistryEngine {

    public static double calculateChemistry(Squad squad) {

        double totalChem = 0;
        for(HashMap.Entry<Position, Player> entry : squad.getLineup().entrySet()) {

            Position pos = entry.getKey();
            Player player = entry.getValue();

            int playerChem = 0;

            Position cur_pos = new Position(player.getPos());
            int init_positional_chem = positionChem(pos, cur_pos);

            int links = 0;

            ArrayList<Position> neighbors = squad.getGraph().getAdjList().get(pos);

            int linksNeeded = neighbors.size();

            for (Position neighbor : neighbors) {
                Player neighbor_player = squad.getLineup().get(neighbor);
                links += numSharedLinks(player, neighbor_player);
            }

            if (init_positional_chem == 0) {
                if (linksNeeded - links <= 0) {
                    playerChem = 2;
                } else if (links == 0) {
                    playerChem = 0;
                } else if (linksNeeded == 2) {
                    if (links == 1) {
                        playerChem = 1;
                    }
                } else if (linksNeeded == 3) {
                    if (links == 1 || links == 2) {
                        playerChem = 1;
                    }
                } else if (linksNeeded == 4) {
                    if (links == 1) {
                        playerChem = 0;
                    } else if (links == 2 || links == 3) {
                        playerChem = 1;
                    }
                } else if (linksNeeded == 5) {
                    if (links == 1) {
                        playerChem = 0;
                    } else if (links == 2 || links == 3 || links == 4) {
                        playerChem = 1;
                    }
                }
            } else if (init_positional_chem == 1) {
                if (linksNeeded - links <= 0) {
                    playerChem = 5;
                } else if (links == 0) {
                    playerChem = 1;
                } else if (linksNeeded == 2) {
                    if (links == 1) {
                        playerChem = 3;
                    }
                } else if (linksNeeded == 3) {
                    if (links == 1 || links == 2) {
                        playerChem = 3;
                    }
                } else if (linksNeeded == 4) {
                    if (links == 1) {
                        playerChem = 1;
                    } else if (links == 2 || links == 3) {
                        playerChem = 3;
                    } else if (linksNeeded == 5) {
                        if (links == 1) {
                            playerChem = 1;
                        } else if (links == 2 || links == 3 || links == 4) {
                            playerChem = 3;
                        }
                    }
                }
            } else if (init_positional_chem == 2) {
                if (linksNeeded - links == 0) {
                    playerChem = 8;
                } else if (linksNeeded - links < 0) {
                    playerChem = 9;
                } else if (links == 0) {
                    playerChem = 2;
                } else if (linksNeeded == 2) {
                    if (links == 1) {
                        playerChem = 5;
                    }
                } else if (linksNeeded == 3) {
                    if (links == 1 || links == 2) {
                        playerChem = 5;
                    }
                } else if (linksNeeded == 4) {
                    if (links == 1) {
                        playerChem = 2;
                    } else if (links == 2 || links == 3) {
                        playerChem = 5;
                    }
                } else if (linksNeeded == 5) {
                    if (links == 1) {
                        playerChem = 2;
                    } else if (links == 2 || links == 3 || links == 4) {
                        playerChem = 5;
                    }
                }
            } else if (init_positional_chem == 3) {
                if (linksNeeded - links == 0) {
                    playerChem = 9;
                } else if (linksNeeded - links < 0) {
                    playerChem = 10;
                } else if (links == 0) {
                    playerChem = 3;
                } else if (linksNeeded == 2) {
                    if (links == 1) {
                        playerChem = 6;
                    }
                } else if (linksNeeded == 3) {
                    if (links == 1 || links == 2) {
                        playerChem = 6;
                    }
                } else if (linksNeeded == 4) {
                    if (links == 1) {
                        playerChem = 3;
                    } else if (links == 2 || links == 3) {
                        playerChem = 6;
                    }
                } else if (linksNeeded == 5) {
                    if (links == 1) {
                        playerChem = 3;
                    } else if (links == 2 || links == 3 || links == 4) {
                        playerChem = 6;
                    }
                }
            }

            //work in progress
//            System.out.println("player: " + player.getName() + " has " + links + "/" + neighbors.size());
//            System.out.println("player: " + player.getName() + " at position " + pos.getActual() + " on " + playerChem + " chem");


            totalChem += playerChem;

        }
        return totalChem;
    }

    public static int numSharedLinks(Player p1, Player p2) {
        int links = 0;

        if (p1.getNation().equals(p2.getNation())) {
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

        if (slot.getBasePos().equals(BasePosition.GK)) {
            if (actual.getBasePos().equals(BasePosition.GK)) {
              return 3;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.CB)) {
            if (actual.getBasePos().equals(BasePosition.CB)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.LB) || actual.getBasePos().equals(BasePosition.RB)) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.RB)) {
            if (actual.getBasePos().equals(BasePosition.RB)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CB) || actual.getBasePos().equals(BasePosition.LB) || actual.getBasePos().equals(BasePosition.RM)) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.LB)) {
            if (actual.getBasePos().equals(BasePosition.LB)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CB) || actual.getBasePos().equals(BasePosition.RB) || actual.getBasePos().equals(BasePosition.LM)) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.CDM)) {
            if (actual.getBasePos().equals(BasePosition.CDM)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CB) || actual.getBasePos().equals(BasePosition.CAM)) {
                return 1;
            } else if (actual.getBasePos().equals(BasePosition.CM)) {
                return 2;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.CAM)) {
            if (actual.getBasePos().equals(BasePosition.CAM)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CF) || actual.getBasePos().equals(BasePosition.CM)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.CDM)) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.CM)) {
            if (actual.getBasePos().equals(BasePosition.CM)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CAM) || actual.getBasePos().equals(BasePosition.CDM)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.LM) || actual.getBasePos().equals(BasePosition.RM)) {
                return 1;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.CF)) {
            if (actual.getBasePos().equals(BasePosition.ST) || actual.getBasePos().equals(BasePosition.CAM)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.CF)) {
                return 3;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.ST)) {
            if (actual.getBasePos().equals(BasePosition.ST)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.CF)) {
                return 2;
            } else {
                return 0;
            }
        } else if (slot.getBasePos().equals(BasePosition.RM)) {
            if (actual.getBasePos().equals(BasePosition.RM)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.RW)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.RF) || actual.getBasePos().equals(BasePosition.RB) || actual.getBasePos().equals(BasePosition.CM)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.LM)) {
            if (actual.getBasePos().equals(BasePosition.LM)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.LW)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.LF) || actual.getBasePos().equals(BasePosition.LB) || actual.getBasePos().equals(BasePosition.CM)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.LW)) {
            if (actual.getBasePos().equals(BasePosition.LW)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.LM) || actual.getBasePos().equals(BasePosition.LF)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.LWB)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.RW)) {
            if (actual.getBasePos().equals(BasePosition.RW)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.RM) || actual.getBasePos().equals(BasePosition.RF)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.RWB)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.LF)) {
            if (actual.getBasePos().equals(BasePosition.LF)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.LW)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.LM) || actual.getBasePos().equals(BasePosition.ST)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.RF)) {
            if (actual.getBasePos().equals(BasePosition.RF)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.RW)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.RM) || actual.getBasePos().equals(BasePosition.ST)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.RWB)) {
            if (actual.getBasePos().equals(BasePosition.RWB)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.RB)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.RW) || actual.getBasePos().equals(BasePosition.RM)) {
                return 1;
            }
        } else if (slot.getBasePos().equals(BasePosition.LWB)) {
            if (actual.getBasePos().equals(BasePosition.LWB)) {
                return 3;
            } else if (actual.getBasePos().equals(BasePosition.LB)) {
                return 2;
            } else if (actual.getBasePos().equals(BasePosition.LW) || actual.getBasePos().equals(BasePosition.LM)) {
                return 1;
            }
        } else {
            return 0;
        }
        return 0;
    }
}
