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

        double curSquadRating = squad.getFractionalSquadRating();
        double curSquadChem = ChemistryEngine.calculateChemistry(squad);

        double ratingDiff = Math.min(curSquadRating - this.minRating, 0);
        double chemDiff = Math.min(curSquadChem - this.minChem, 0);

        double ratingScore = 50 + ratingDiff;
        double chemScore = 50 + chemDiff;
        double priceScore = squad.getSquadPrice();


        if (curSquadRating < minRating || curSquadChem < minChem) {
            return (ratingScore + chemScore);
        } else {
            return 299 - 14.4*Math.log(priceScore);
//            return 1050 - 0.001*priceScore;
//            return (priceScore/100);
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
                System.out.println("CUR: " + currentScore);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore);
                    bestScore = currentScore;
                    bestSquad = current;
                }

                int randomInd = SBChallenge.getRandomNumber(11);
                int randomPlayer = SBChallenge.getRandomNumber(availablePlayers.size());
                Squad newSquad = Squad.newAtPos(current, randomInd, availablePlayers.get(randomPlayer)); //does a deep copy

//                int randomInd2 = SBChallenge.getRandomNumber(11);
//                int randomPlayer2 = SBChallenge.getRandomNumber(availablePlayers.size());
//                Squad newSquad2 = Squad.newAtPos(newSquad, randomInd2, availablePlayers.get(randomPlayer2)); //does a deep copy

                double newScore = getFitnessScore(newSquad);

                if (simAnnealing) {
                    double delta = newScore - currentScore;
                    double ap = Math.pow(Math.E, delta/T);
                    if (newScore > currentScore) {
                        current = newSquad;
                    } else {
                        if (ap < Math.random()) {
                            current = newSquad;
                        } else {
                            //keep what we had
                        }
                    }

                } else if (hillClimbing) {
                    if (newScore > currentScore) {
                        current = newSquad;
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
