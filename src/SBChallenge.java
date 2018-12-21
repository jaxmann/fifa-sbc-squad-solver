public class SBChallenge {

    private Squad squad;
    private int minRating;
    private int minChem;


    public SBChallenge(Squad squad, int minRating, int minChem) {
        this.squad = squad;
        this.minRating = minRating;
        this.minChem = minChem;
    }


//    public double getFitnessScore() {
//
//        double thisRating = Squad.getSquadRating(this.squad);
//        int thisChem = ChemistryEngine.calculateChemistry(this.squad);
//
//        if (thisRating < minRating || thisChem < minChem) {
//            return 0.0;
//        } else {
//            return 1000/this.squad.getSquadPrice();
//        }
//
//    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMinChem() {
        return minChem;
    }

    public void setMinChem(int minChem) {
        this.minChem = minChem;
    }
}
