import java.util.ArrayList;
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

    //pass in an "Annealing Params" instead of a bunch of variables
    public void runSimulatedAnnealing(Squad current, ArrayList<Player> availablePlayers, boolean simAnnealing, boolean hillClimbing) {

        Squad bestSquad = current;

        double T = 1.0;
        double Tmin = 0.0001;
        double alpha = 0.95;
        int numIterations = 1000;

        double bestScore = this.getFitnessScore(current);

        System.out.println(this.getFitnessScore(current));
        System.out.println(current.getSquadPrice());

        while (T  > Tmin) {
            for (int i=0; i<numIterations; i++) {

                double currentScore = this.getFitnessScore(current);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore + ", previously: " + bestScore);
                    bestScore = currentScore;
                    bestSquad = current;
                }

                int randomInd = SBChallenge.getRandomNumber(11);
                int randomPlayer = SBChallenge.getRandomNumber(availablePlayers.size());
                Squad newSquad = Squad.newAtPos(current, randomInd, availablePlayers.get(randomPlayer));

                double newScore = this.getFitnessScore(newSquad);

                if (simAnnealing) {
                    double ap = Math.pow(Math.E, (currentScore - newScore)/T);

                    if (ap > Math.random()) {
//                        System.out.println("update because new score is: " + newScore + " and old is: " + currentScore);
                        current = newSquad;
                    }
                } else if (hillClimbing) {

                    if (newScore > currentScore) {
//                        System.out.println("update because new score is: " + newScore + " and old is: " + currentScore);
                        current = newSquad; //hill climbing
                    }
                }

            }
            T *= alpha;
        }

        System.out.println(bestScore);
        bestSquad.printSquad();
        System.out.println("PRICE:" + bestSquad.getSquadPrice());
        System.out.println("CHEM: " + ChemistryEngine.calculateChemistry(bestSquad));
        System.out.println("RATING: " + bestSquad.getSquadRating());
        System.out.println("SCORE: " + this.getFitnessScore(bestSquad));



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
