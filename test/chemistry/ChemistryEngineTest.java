package chemistry;

import org.junit.jupiter.api.BeforeEach;
import player.*;
import squad.Squad;
import org.junit.jupiter.api.Test;
import squad.SquadHelper;
import squad.formation.Formation;
import squad.formation.FormationFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChemistryEngineTest {

    private PlayerLoaderUtil pl;

    @BeforeEach
    void setUp() throws IOException {
        pl = new PlayerLoaderUtil();
        pl.loadPlayers(false);
    }

    @Test
    void test_calculateChemistry_nationLinksOnly() throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        Player p1 = new Player("name", "team1", "germany", "league1", BasePosition.RB);
        Player p2 = new Player("name", "team2", "germany", "league2", BasePosition.LB);
        Player p3 = new Player("name", "team3", "germany", "league3", BasePosition.GK);
        Player p4 = new Player("name", "team4", "germany", "league4", BasePosition.CB);
        Player p5 = new Player("name", "team5", "germany", "league5", BasePosition.CB);
        Player p6 = new Player("name", "team6", "germany", "league6", BasePosition.RM);
        Player p7 = new Player("name", "team7", "germany", "league7", BasePosition.CDM);
        Player p8 = new Player("name", "team8", "germany", "league8", BasePosition.LM);
        Player p9 = new Player("name", "team9", "germany", "league9", BasePosition.ST);
        Player p10 = new Player("name", "team10", "germany", "league10", BasePosition.ST);
        Player p11 = new Player("name", "team11", "germany", "league11", BasePosition.CAM);

        players.add(p1); //rb
        players.add(p2); //lb
        players.add(p3); //gk
        players.add(p4); //lcb
        players.add(p5); //rcb
        players.add(p6); //rm
        players.add(p7); //cdm
        players.add(p8); //lm
        players.add(p9); //lst
        players.add(p10); //rst
        players.add(p11); //cam

        Squad s = new Squad(positions, players, f);

        //this could change as new player dataset is imported
        assertEquals(99, ChemistryEngine.calculateChemistry(s));
    }

    @Test
    void test_numSharedLinks_none() {
        Player p1 = new Player("player1", "bayern", "germany", "bundesliga", BasePosition.CB);
        Player p2 = new Player("player1", "manchester city", "england", "premier league", BasePosition.CB);
        int numSharedLinks = ChemistryEngine.numSharedLinks(p1, p2);
        assertEquals(0, numSharedLinks);
    }

    @Test
    void test_numSharedLinks_one() {
        Player p1 = new Player("player1", "bayern", "germany", "bundesliga", BasePosition.CB);
        Player p2 = new Player("player1", "manchester city", "germany", "premier league", BasePosition.CB);
        int numSharedLinks = ChemistryEngine.numSharedLinks(p1, p2);
        assertEquals(1, numSharedLinks);
    }

    @Test
    void test_numSharedLinks_two() {
        Player p1 = new Player("player1", "bayern", "germany", "bundesliga", BasePosition.CB);
        Player p2 = new Player("player1", "dortmund", "germany", "bundesliga", BasePosition.CB);
        int numSharedLinks = ChemistryEngine.numSharedLinks(p1, p2);
        assertEquals(2, numSharedLinks);
    }

    @Test
    void test_numSharedLinks_three() {
        Player p1 = new Player("player1", "dortmund", "germany", "bundesliga", BasePosition.CB);
        Player p2 = new Player("player1", "dortmund", "germany", "bundesliga", BasePosition.CB);
        int numSharedLinks = ChemistryEngine.numSharedLinks(p1, p2);
        assertEquals(3, numSharedLinks);
    }

    @Test
    void test_positionChem_LB_at_LWB() {
        Position slot = new Position("LWB");
        Position actual = new Position("LB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_LB_at_CB() {
        Position slot = new Position("CB");
        Position actual = new Position("LB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LB_at_LB() {
        Position slot = new Position("LB");
        Position actual = new Position("LB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_LWB_at_LW() {
        Position slot = new Position("LW");
        Position actual = new Position("LWB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LM_at_CM() {
        Position slot = new Position("CM");
        Position actual = new Position("LM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LM_at_LM() {
        Position slot = new Position("LM");
        Position actual = new Position("LM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_LM_at_RM() {
        Position slot = new Position("RM");
        Position actual = new Position("LM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LM_at_LB() {
        Position slot = new Position("LB");
        Position actual = new Position("LM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LW_at_LWB() {
        Position slot = new Position("LWB");
        Position actual = new Position("LM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LW_at_LW() {
        Position slot = new Position("LW");
        Position actual = new Position("LW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_LW_at_RW() {
        Position slot = new Position("RW");
        Position actual = new Position("LW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LW_at_LF() {
        Position slot = new Position("LF");
        Position actual = new Position("LW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_LF_at_ST() {
        Position slot = new Position("ST");
        Position actual = new Position("LF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_LF_at_LF() {
        Position slot = new Position("LW");
        Position actual = new Position("LW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_LF_at_LW() {
        Position slot = new Position("LW");
        Position actual = new Position("LF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_LF_at_LM() {
        Position slot = new Position("LM");
        Position actual = new Position("LF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RF_at_ST() {
        Position slot = new Position("ST");
        Position actual = new Position("RF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RF_at_RF() {
        Position slot = new Position("RW");
        Position actual = new Position("RW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_RF_at_RW() {
        Position slot = new Position("RW");
        Position actual = new Position("RF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_RF_at_RM() {
        Position slot = new Position("RM");
        Position actual = new Position("RF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RW_at_RWB() {
        Position slot = new Position("RWB");
        Position actual = new Position("RM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RW_at_RW() {
        Position slot = new Position("RW");
        Position actual = new Position("RW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_RW_at_LW() {
        Position slot = new Position("LW");
        Position actual = new Position("RW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RW_at_RF() {
        Position slot = new Position("RF");
        Position actual = new Position("RW");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_RM_at_CM() {
        Position slot = new Position("CM");
        Position actual = new Position("RM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_RM_at_RM() {
        Position slot = new Position("RM");
        Position actual = new Position("RM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_RM_at_LM() {
        Position slot = new Position("LM");
        Position actual = new Position("RM");
        int c1 = ChemistryEngine.positionChem(slot, actual);
        assertEquals(c1, 1);
    }

    @Test
    void test_positionChem_RM_at_RB() {
        Position slot = new Position("RB");
        Position actual = new Position("RM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CB_at_RB() {
        Position slot = new Position("RB");
        Position actual = new Position("CB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CB_at_LB() {
        Position slot = new Position("LB");
        Position actual = new Position("CB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CB_at_CB() {
        Position slot = new Position("CB");
        Position actual = new Position("CB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_CB_at_CDM() {
        Position slot = new Position("CDM");
        Position actual = new Position("CB");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_GK_at_GK() {
        Position slot = new Position("GK");
        Position actual = new Position("GK");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_GK_at_anywhereElse() {
        Position slot = new Position("GK");
        for (BasePosition basePos : BasePosition.values()) {
            if (!basePos.equals(BasePosition.GK)) {
                assertEquals(0, ChemistryEngine.positionChem(slot, new Position(basePos)));
            }
        }
    }

    @Test
    void test_positionChem_CM_at_CM() {
        Position slot = new Position("CM");
        Position actual = new Position("CM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_CM_at_CAM() {
        Position slot = new Position("CAM");
        Position actual = new Position("CM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CM_at_CDM() {
        Position slot = new Position("CAM");
        Position actual = new Position("CM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CM_at_LM() {
        Position slot = new Position("LM");
        Position actual = new Position("CM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CM_at_RM() {
        Position slot = new Position("LM");
        Position actual = new Position("CM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CDM_at_CAM() {
        Position slot = new Position("CAM");
        Position actual = new Position("CDM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CDM_at_CDM() {
        Position slot = new Position("CDM");
        Position actual = new Position("CDM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_CDM_at_CB() {
        Position slot = new Position("CB");
        Position actual = new Position("CDM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CDM_at_CM() {
        Position slot = new Position("CM");
        Position actual = new Position("CDM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CAM_at_CM() {
        Position slot = new Position("CM");
        Position actual = new Position("CAM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CAM_at_CAM() {
        Position slot = new Position("CAM");
        Position actual = new Position("CAM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_CAM_at_CF() {
        Position slot = new Position("CF");
        Position actual = new Position("CAM");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CF_at_CF() {
        Position slot = new Position("CF");
        Position actual = new Position("CF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_CF_at_ST() {
        Position slot = new Position("ST");
        Position actual = new Position("CF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CF_at_CAM() {
        Position slot = new Position("CAM");
        Position actual = new Position("CF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_CF_at_LF() {
        Position slot = new Position("LF");
        Position actual = new Position("CF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_CF_at_RF() {
        Position slot = new Position("RF");
        Position actual = new Position("CF");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_ST_at_ST() {
        Position slot = new Position("ST");
        Position actual = new Position("ST");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(3, chem);
    }

    @Test
    void test_positionChem_ST_at_CF() {
        Position slot = new Position("CF");
        Position actual = new Position("ST");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(2, chem);
    }

    @Test
    void test_positionChem_ST_at_LF() {
        Position slot = new Position("LF");
        Position actual = new Position("ST");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

    @Test
    void test_positionChem_ST_at_RF() {
        Position slot = new Position("RF");
        Position actual = new Position("ST");
        int chem = ChemistryEngine.positionChem(slot, actual);
        assertEquals(1, chem);
    }

}