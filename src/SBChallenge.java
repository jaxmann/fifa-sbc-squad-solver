//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class SBChallenge {

    private Constraints constraints;

    public SBChallenge(Constraints constraints) {
        this.constraints = constraints;
    }

    public double getFitnessScore(Squad squad) {

        double curSquadRating = squad.getFractionalSquadRating();
        double curSquadChem = ChemistryEngine.calculateChemistry(squad);

        int minRating = this.constraints.getMinRating();
        int minChem = this.constraints.getMinChem();

        double ratingDiff = Math.abs(curSquadRating - minRating);
        double chemDiff = Math.abs(curSquadChem - minChem);

//        double ratingScore = 50 + ratingDiff;
//        double chemScore = 50 + chemDiff;
        double combinedDifference = ratingDiff + chemDiff;
        double priceScore = squad.getSquadPrice();

//        System.out.println("ratingScore: " + ratingScore + " chemScore: " + chemScore + " priceScore: " + priceScore);


        if (curSquadRating < minRating || curSquadChem < minChem) {
            return (200 - combinedDifference);
        } else {
            return 481.1 - 21.5*Math.log(priceScore) + 100;
            //fit {(750000, 100), (500000, 105), (200000, 110), (100000, 120), (50000, 140), (25000, 160), (15000, 180), (10000, 190), (5000, 200)}//            return 1050 - 0.001*priceScore;
//            return (priceScore/100);
        }


    }

    //pass in an "Annealing Params" instead of a bunch of variables
    public void runSimulatedAnnealing(Squad current, ArrayList<Player> availablePlayers, boolean simAnnealing, boolean hillClimbing) {

        Squad bestSquad = current;

        double T = 1.0;
        double Tmin = 0.0001;
        double alpha = 0.95;
        int numIterations = 10000;
        int maxSwaps = 8; //max number is 11 (potentially swap all 11 players during an iteration)


        double bestScore = getFitnessScore(current);

        System.out.println("INIT fitness score: " + getFitnessScore(current));
        System.out.println("INIT squad price: " + current.getSquadPrice());

        while (T  > Tmin) {
            for (int i=0; i<numIterations; i++) {

                double currentScore = getFitnessScore(current);
//                System.out.println("CUR: " + currentScore);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore);
                    bestScore = currentScore;
                    bestSquad = current;
                }

                int numSwaps = ThreadLocalRandom.current().nextInt(1, maxSwaps + 1);
                Squad newSquad = current;
                while (numSwaps > 0) {

                    int randomInd = SBChallenge.getRandomNumber(11);
                    int randomPlayer = SBChallenge.getRandomNumber(availablePlayers.size());
                    newSquad = Squad.newAtPos(newSquad, randomInd, availablePlayers.get(randomPlayer)); //does a deep copy

                    numSwaps--;

                }

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

        if (bestScore < 200) {
            System.out.println("No solution found that satisfies CSP");
        } else {
            System.out.println(bestScore);
            bestSquad.printSquad();
            System.out.println("PRICE:" + bestSquad.getSquadPrice());
            System.out.println("CHEM: " + ChemistryEngine.calculateChemistry(bestSquad));
            System.out.println("RATING: " + bestSquad.getSquadRating());
            System.out.println("SCORE: " + getFitnessScore(bestSquad));
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

}
