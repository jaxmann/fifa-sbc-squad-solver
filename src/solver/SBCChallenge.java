package solver;//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import chemistry.ChemistryEngine;
import constraint.Constraint;
import constraint.ConstraintType;
import constraint.Constraints;
import player.Player;
import player.PlayerLoaderUtil;
import squad.Squad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class SBCChallenge {

    private Constraints constraints;

    public SBCChallenge(Constraints constraints) {
        this.constraints = constraints;
    }

    // "fitness" of squad is a measure of how "good" the result is
    public double getFitnessScore(Squad squad) {

        double curSquadRating = squad.getFractionalSquadRating();
        double curSquadChem = ChemistryEngine.calculateChemistry(squad);

        int minRating = this.constraints.getMinRating();
        int minChem = this.constraints.getMinChem();

        double ratingDiff = Math.abs(curSquadRating - minRating);
        double chemDiff = Math.abs(curSquadChem - minChem);

        double combinedDifference = ratingDiff + chemDiff;
        double priceScore = squad.getSquadPrice();

//        System.out.println("ratingScore: " + ratingScore + " chemScore: " + chemScore + " priceScore: " + priceScore);

        if (!squad.doesSquadSatisfyAllConstaints(this.constraints)) {
            double score = 0;
            for (Constraint constraint : this.constraints.getConstraints()) {
                if (squad.doesSquadSatisfyConstraint(constraint)) {
                    score += 33.0;
                }
            }
            return score;
        } else if (curSquadRating < minRating || curSquadChem < minChem) {
            return (200 - combinedDifference);
        } else {
            return 481.1 - 21.5*Math.log(priceScore) + 100;
            //fit {(750000, 100), (500000, 105), (200000, 110), (100000, 120), (50000, 140), (25000, 160), (15000, 180), (10000, 190), (5000, 200)}//            return 1050 - 0.001*priceScore;
//            return (priceScore/100);
        }


    }

    // high level - generate 3-5 'seed' squads, branch out in some type of BFS/genetic algorithm, try each squad, then start at seed=1 again etc
    // TODO add plotting to track what is actually working
    public void runSimulatedAnnealing(Squad current, ArrayList<Player> availablePlayers, boolean simAnnealing, boolean hillClimbing) throws IOException {

        Squad bestSquad = current;

        double T = 1.0;
        double Tmin = 0.0001;
        double alpha = 0.95;
        int numIterations = 1000;
        // maybe let this number decay - if a valid solution is found, don't swap out as many players from it?
        double maxSwaps = 8.0; //max number is 11 (potentially swap all 11 players during an iteration)

        double bestScore = getFitnessScore(current);

        System.out.println("INIT fitness score: " + getFitnessScore(current));
        System.out.println("INIT squad price: " + current.getSquadPrice());

        while (T  > Tmin) {
            for (int i=0; i<numIterations; i++) {

                double currentScore = getFitnessScore(current);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore);
                    bestScore = currentScore;
                    bestSquad = current;
//                    maxSwaps *=.95;
//                    System.out.println("updating max swaps to: " + maxSwaps);
                }

                int numSwaps = ThreadLocalRandom.current().nextInt(1, (int) (maxSwaps + 1));
                // do n swaps in 1 func
                Squad newSquad = Squad.swapNRandomPlayers(numSwaps, current, availablePlayers);

//                if (newSquad.getFractionalSquadRating() < 80) {
//                    newSquad.printSquad();
//                }
//
                for (Constraint constraint : constraints.getConstraints()) {
                    if (constraint.getConstraintType() == ConstraintType.MINRATING && newSquad.doesSquadSatisfyConstraint(constraint)) {
                        newSquad = Squad.optimizeRatingWithoutReducingChem(newSquad, 3);
                    }
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

}
