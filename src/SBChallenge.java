import java.util.Random;

public class SBChallenge {


    private int minRating;
    private int minChem;


    public SBChallenge(int minRating, int minChem) {
        this.minRating = minRating;
        this.minChem = minChem;
    }


    public double getFitnessScore(Squad squad) {

        double thisRating = squad.getSquadRating();
        int thisChem = ChemistryEngine.calculateChemistry(squad);

        //come up with better fitness function
//        double score = (minRating - thisRating)
        if (thisRating < minRating || thisChem < minChem) {
            return 0.0;
        } else {
            return 100000000/squad.getSquadPrice(); //100 million times 1/price
        }

    }

    public static Integer[] getRandomNumsBetween(int maxValExclusive, int numNums) {
        Integer[] output = new Integer[numNums];
        long seed = new Long(10);

        Random randomGenerator = new Random();
        randomGenerator.setSeed(seed);

        for (int i=0; i<numNums; i++) {
            output[i] = randomGenerator.nextInt(maxValExclusive);
        }

        return output;
    }

    public static int getRandomNumber(int maxValExclusive) {
//        long seed = new Long(10);

        Random randomGenerator = new Random();
//        randomGenerator.setSeed(seed);

        return randomGenerator.nextInt(maxValExclusive);
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
