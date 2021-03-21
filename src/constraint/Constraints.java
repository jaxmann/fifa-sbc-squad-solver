package constraint;

import java.util.ArrayList;

public class Constraints {

    private ArrayList<Constraint> constraints;

    public Constraints() {
        this.constraints = new ArrayList<>();
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    // just for min rating and chem - helper methods that searches through all constraints so can call it on constraints array
    public int getMinRating() {
        for (Constraint c: this.constraints) {
            if (c.getConstraintType() == ConstraintType.MINRATING) {
                return c.getMinRating();
            }
        }
        return 0;
    }

    public int getMinChem() {
        for (Constraint c: this.constraints) {
            if (c.getConstraintType() == ConstraintType.MINCHEM) {
                return c.getMinChem();
            }
        }
        return 0;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}