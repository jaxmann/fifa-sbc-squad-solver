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

        Squad s = SquadHelper.create75DefaultSquad();
        s.setBrick(brick);

        assertTrue(s.doesSquadSatisfyConstraints(brickConstraint));
    }

    @Test
    public void test_singleComplexBrickConstraintNotSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK), "Germany", "Bundesliga", "Bayern");
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create75DefaultSquad();
        Player incorrectBrickedPlayer = new Player(Squad.BRICKED_PLAYER_NAME, "Bayern", "Germany", "Premier League", BasePosition.GK);
        s.updateAtPos(BasePosition.GK.toString(), incorrectBrickedPlayer);

        assertFalse(s.doesSquadSatisfyConstraints(brickConstraint));
    }



}
