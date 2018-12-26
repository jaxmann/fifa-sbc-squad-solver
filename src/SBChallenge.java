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

        double thisRating = squad.getFractionalSquadRating();
        double thisChem = ChemistryEngine.calculateChemistry(squad);

        double ratingDiff = Math.abs(thisRating - this.minRating);
        double chemDiff = Math.abs(thisChem - this.minChem);

        double ratingScore = 50 - ratingDiff;
        double chemScore = 50 - chemDiff;
        double priceScore = squad.getSquadPrice();


        if (thisRating < minRating || thisChem < minChem) {
            return (ratingScore + chemScore);
        } else {
            return (1000000/priceScore);
        }


    }

    //pass in an "Annealing Params" instead of a bunch of variables
    public void runSimulatedAnnealing(Squad current, ArrayList<Player> availablePlayers, boolean simAnnealing, boolean hillClimbing) {

        Squad bestSquad = current;

        double T = 1.0;
        double Tmin = 0.0001;
        double alpha = 0.95;
        int numIterations = 1000;

        double bestScore = getFitnessScore(current);

        System.out.println("INIT fitness score: " + getFitnessScore(current));
        System.out.println("INIT squad price: " + current.getSquadPrice());

        while (T  > Tmin) {
            for (int i=0; i<numIterations; i++) {

                double currentScore = getFitnessScore(current);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore + ", previously: " + bestScore);
                    bestScore = currentScore;
                    bestSquad = current;
                }

                int randomInd = SBChallenge.getRandomNumber(11);
                int randomPlayer = SBChallenge.getRandomNumber(availablePlayers.size());
                Squad newSquad = Squad.newAtPos(current, randomInd, availablePlayers.get(randomPlayer)); //does a deep copy

                double newScore = getFitnessScore(newSquad);

                if (simAnnealing) {
                    double ap = Math.pow(Math.E, (currentScore - newScore)/T);

                    if (ap < Math.random()) {
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
        System.out.println("SCORE: " + getFitnessScore(bestSquad));



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
