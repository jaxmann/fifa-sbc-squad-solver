import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ConstraintTest {

    @Test
    public void test_minChemConstraintSatisfied() {
        Constraint minChemConstraint = new Constraint(ConstraintType.MINCHEM);
        minChemConstraint.setMinChem(71);

        Squad s = SquadHelper.create71ChemSquad();

        assertTrue(s.doesSquadSatisfyConstraints(minChemConstraint));
    }

    @Test
    public void test_minChemConstraintNotSatisfied() {
        Constraint minChemConstraint = new Constraint(ConstraintType.MINCHEM);
        minChemConstraint.setMinChem(85);

        Squad s = SquadHelper.create71ChemSquad();

        assertFalse(s.doesSquadSatisfyConstraints(minChemConstraint));
    }

    @Test
    public void test_minRatingConstraintSatisfied() {
        Constraint minRatingConstraint = new Constraint(ConstraintType.MINRATING);
        minRatingConstraint.setMinRating(87);

        Squad s = SquadHelper.create87DefaultSquad();

        assertTrue(s.doesSquadSatisfyConstraints(minRatingConstraint));
    }

    @Test
    public void test_minRatingConstraintNotSatisfied() {
        Constraint minRatingConstraint = new Constraint(ConstraintType.MINRATING);
        minRatingConstraint.setMinRating(88);

        Squad s = SquadHelper.create87DefaultSquad();

        assertFalse(s.doesSquadSatisfyConstraints(minRatingConstraint));
    }

    @Test
    public void test_singleSimpleBrickConstraintSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK));
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create87DefaultSquad();
        s.setBrick(brick);

        assertTrue(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_singleSimpleBrickConstraintNotSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK));
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create87DefaultSquad();

        assertFalse(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_singleComplexBrickConstraintSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK), "Germany", "Bundesliga", "Bayern");
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.setBrick(brick);

        assertTrue(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_singleComplexBrickConstraintNotSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK), "Germany", "Bundesliga", "Bayern");
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        Player incorrectBrickedPlayer = new Player(Squad.BRICKED_PLAYER_NAME, "Bayern", "Germany", "Premier League", BasePosition.GK);
        s.updateAtPos(ActualPosition.GK, incorrectBrickedPlayer);

        assertFalse(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_multipleComplexBrickConstraintSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick1 = new Brick(new Position(BasePosition.GK), "Germany", "Bundesliga", "Bayern");
        Brick brick2 = new Brick(new Position(BasePosition.CB, ActualPosition.RCB), "Germany", "Bundesliga");
        ArrayList<Brick> bricks = new ArrayList<>();
        bricks.add(brick1);
        bricks.add(brick2);
        brickConstraint.setBricks(bricks);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.setBrick(brick1);
        s.setBrick(brick2);

        assertTrue(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_multipleComplexBrickConstraintNotSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick1 = new Brick(new Position(BasePosition.GK), "Germany", "Bundesliga", "Bayern");
        Brick brick2 = new Brick(new Position(BasePosition.CB, ActualPosition.RCB), "Germany", "Bundesliga");
        ArrayList<Brick> bricks = new ArrayList<>();
        bricks.add(brick1);
        bricks.add(brick2);
        brickConstraint.setBricks(bricks);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        Player incorrectBrickedPlayer1 = new Player(Squad.BRICKED_PLAYER_NAME, "Bayern", "Germany", "Premier League", BasePosition.GK);
        Player incorrectBrickedPlayer2 = new Player(Squad.BRICKED_PLAYER_NAME, null, "Germany", "Premier League", BasePosition.CB);

        s.updateAtPos(ActualPosition.GK, incorrectBrickedPlayer1);
        s.updateAtPos(ActualPosition.RCB, incorrectBrickedPlayer2);

        assertFalse(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_minOfCardTypeConstraintSatisfied() {
        Constraint minOfCardTypeConstraint = new Constraint(ConstraintType.MIN_OF_CARDTYPE);
        minOfCardTypeConstraint.setCardType(CardType.GOLD_IF);
        minOfCardTypeConstraint.setMin_of_cardtype(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(minOfCardTypeConstraint));
    }

    @Test
    public void test_minOfCardTypeConstraintNotSatisfied() {
        Constraint minOfCardTypeConstraint = new Constraint(ConstraintType.MIN_OF_CARDTYPE);
        minOfCardTypeConstraint.setCardType(CardType.GOLD_IF);
        minOfCardTypeConstraint.setMin_of_cardtype(4);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(minOfCardTypeConstraint));
    }

    @Test
    public void test_exactOfCardTypeConstraintSatisfied() {
        Constraint exactOfCardTypeConstraint = new Constraint(ConstraintType.EXACT_OF_CARDTYPE);
        exactOfCardTypeConstraint.setCardType(CardType.GOLD_IF);
        exactOfCardTypeConstraint.setExact_of_cardtype(3);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(exactOfCardTypeConstraint));
    }

    @Test
    public void test_exactOfCardTypeConstraintNotSatisfied() {
        Constraint exactOfCardTypeConstraint = new Constraint(ConstraintType.EXACT_OF_CARDTYPE);
        exactOfCardTypeConstraint.setCardType(CardType.GOLD_IF);
        exactOfCardTypeConstraint.setExact_of_cardtype(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(exactOfCardTypeConstraint));
    }

    @Test
    public void test_minOfClubConstraintSatisfied() {
        Constraint minOfCardTypeConstraint = new Constraint(ConstraintType.MIN_OF_CLUB);
        minOfCardTypeConstraint.setClub("Bayern");
        minOfCardTypeConstraint.setMin_of_club(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(minOfCardTypeConstraint));
    }

    @Test
    public void test_minOfClubConstraintNotSatisfied() {
        Constraint minOfCardTypeConstraint = new Constraint(ConstraintType.MIN_OF_CLUB);
        minOfCardTypeConstraint.setClub("Bayern");
        minOfCardTypeConstraint.setMin_of_club(4);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(minOfCardTypeConstraint));
    }

    @Test
    public void test_exactOfClubConstraintSatisfied() {
        Constraint exactOfClubConstraint = new Constraint(ConstraintType.EXACT_OF_CLUB);
        exactOfClubConstraint.setClub("Bayern");
        exactOfClubConstraint.setExact_of_club(3);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(exactOfClubConstraint));
    }

    @Test
    public void test_exactOfClubConstraintNotSatisfied() {
        Constraint exactOfClubConstraint = new Constraint(ConstraintType.EXACT_OF_CLUB);
        exactOfClubConstraint.setClub("Bayern");
        exactOfClubConstraint.setExact_of_club(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(exactOfClubConstraint));
    }

    @Test
    public void test_minOfNationConstraintSatisfied() {
        Constraint minOfNationConstraint = new Constraint(ConstraintType.MIN_OF_NATION);
        minOfNationConstraint.setNation("Germany");
        minOfNationConstraint.setMin_of_nation(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(minOfNationConstraint));
    }

    @Test
    public void test_minOfNationConstraintNotSatisfied() {
        Constraint minOfNationConstraint = new Constraint(ConstraintType.MIN_OF_NATION);
        minOfNationConstraint.setNation("Germany");
        minOfNationConstraint.setMin_of_nation(4);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(minOfNationConstraint));
    }

    @Test
    public void test_exactOfNationConstraintSatisfied() {
        Constraint exactOfClubConstraint = new Constraint(ConstraintType.EXACT_OF_NATION);
        exactOfClubConstraint.setNation("Germany");
        exactOfClubConstraint.setExact_of_nation(3);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(exactOfClubConstraint));
    }

    @Test
    public void test_exactOfNationConstraintNotSatisfied() {
        Constraint exactOfClubConstraint = new Constraint(ConstraintType.EXACT_OF_NATION);
        exactOfClubConstraint.setNation("Germany");
        exactOfClubConstraint.setExact_of_nation(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(exactOfClubConstraint));
    }

    @Test
    public void test_minOfLeagueConstraintSatisfied() {
        Constraint minOfLeagueConstraint = new Constraint(ConstraintType.MIN_OF_LEAGUE);
        minOfLeagueConstraint.setLeague("Bundesliga");
        minOfLeagueConstraint.setMin_of_league(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(minOfLeagueConstraint));
    }

    @Test
    public void test_minOfLeagueConstraintNotSatisfied() {
        Constraint minOfLeagueConstraint = new Constraint(ConstraintType.MIN_OF_LEAGUE);
        minOfLeagueConstraint.setLeague("Bundesliga");
        minOfLeagueConstraint.setMin_of_league(4);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(minOfLeagueConstraint));
    }

    @Test
    public void test_exactOfLeagueConstraintSatisfied() {
        Constraint exactOfLeagueConstraint = new Constraint(ConstraintType.EXACT_OF_LEAGUE);
        exactOfLeagueConstraint.setLeague("Bundesliga");
        exactOfLeagueConstraint.setExact_of_league(3);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertTrue(s.doesSquadSatisfyConstraints(exactOfLeagueConstraint));
    }

    @Test
    public void test_exactOfLeagueConstraintNotSatisfied() {
        Constraint exactOfLeagueConstraint = new Constraint(ConstraintType.EXACT_OF_LEAGUE);
        exactOfLeagueConstraint.setLeague("Bundesliga");
        exactOfLeagueConstraint.setExact_of_league(2);

        Player validPlayer1 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.GK, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer2 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);
        Player validPlayer3 = new Player("test player", "Bayern", "Germany", "Bundesliga", BasePosition.CB, CardType.GOLD_IF, 800, 85, false);

        Squad s = SquadHelper.create75GoldNonRareTestSquad();
        s.updateAtPos(ActualPosition.GK, validPlayer1);
        s.updateAtPos(ActualPosition.LCB, validPlayer2);
        s.updateAtPos(ActualPosition.RCB, validPlayer3);

        assertFalse(s.doesSquadSatisfyConstraints(exactOfLeagueConstraint));
    }

}
