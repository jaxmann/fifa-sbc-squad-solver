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

        assertTrue(s.doesSquadSatisfyConstraint(minChemConstraint));
    }

    @Test
    public void test_minChemConstraintNotSatisfied() {
        Constraint minChemConstraint = new Constraint(ConstraintType.MINCHEM);
        minChemConstraint.setMinChem(85);

        Squad s = SquadHelper.create71ChemSquad();

        assertFalse(s.doesSquadSatisfyConstraint(minChemConstraint));
    }

    @Test
    public void test_minRatingConstraintSatisfied() {
        Constraint minRatingConstraint = new Constraint(ConstraintType.MINRATING);
        minRatingConstraint.setMinRating(87);

        Squad s = SquadHelper.create87DefaultSquad();

        assertTrue(s.doesSquadSatisfyConstraint(minRatingConstraint));
    }

    @Test
    public void test_minRatingConstraintNotSatisfied() {
        Constraint minRatingConstraint = new Constraint(ConstraintType.MINRATING);
        minRatingConstraint.setMinRating(88);

        Squad s = SquadHelper.create87DefaultSquad();

        assertFalse(s.doesSquadSatisfyConstraint(minRatingConstraint));
    }

    @Test
    public void test_singleBrickConstraintSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);
        Brick brick = new Brick(new Position(BasePosition.GK));
        brickConstraint.setBricks(new ArrayList<>(Arrays.asList(brick)));

        Squad s = SquadHelper.create87DefaultSquad();
        s.setBrick(brick);

        assertTrue(s.doesSquadSatisfyConstraint(brickConstraint));
    }

    @Test
    public void test_singleBrickConstraintNotSatisfied() {
        Constraint brickConstraint = new Constraint(ConstraintType.BRICKS);

        Squad s = SquadHelper.create87DefaultSquad();

        assertFalse(s.doesSquadSatisfyConstraint(brickConstraint));
    }

}
