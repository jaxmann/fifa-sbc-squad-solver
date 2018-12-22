import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SBChallengeTest {

    @Test
    void simulatedAnnealing() {

        Squad current = Squad.createDefaultSquad();
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players83Plus = pl.getAll83Plus();

        double minCost = current.getSquadPrice();

        boolean simAnnealing = true;
        boolean hillClimbing =false;

        int minRating = 87;
        int minChem = 70;
        Squad bestSquad = current;

        double T = 1.0;
        double Tmin = 0.0001;
        double alpha = 0.95;
        int numIterations = 10000;

        SBChallenge sbc = new SBChallenge(minRating, minChem);
        double bestScore = sbc.getFitnessScore(current);
        System.out.println(sbc.getFitnessScore(current));
        System.out.println(current.getSquadPrice());


        while (T  > Tmin) {
            for (int i=0; i<numIterations; i++) {

                double currentScore = sbc.getFitnessScore(current);

                if (currentScore > bestScore) {
                    System.out.println("updated bestscore with: " + currentScore + ", previously: " + bestScore);
                    bestScore = currentScore;
                    bestSquad = current;
                }

                int randomInd = SBChallenge.getRandomNumber(11);
                int randomPlayer = SBChallenge.getRandomNumber(players83Plus.size());
                Squad newSquad = Squad.newAtPos(current, randomInd, players83Plus.get(randomPlayer));

                double newScore = sbc.getFitnessScore(newSquad);

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
        System.out.println(bestSquad.getSquadPrice());
        System.out.println("CHEM: " + ChemistryEngine.calculateChemistry(bestSquad));
        System.out.println("RATING: " + bestSquad.getSquadRating());
        System.out.println("SCORE: " + sbc.getFitnessScore(bestSquad));

//        for (int i=0; i<50000; i++) {
//            Squad temp = last;
//
//            int randomInd = SBChallenge.getRandomNumber(11);
//            int randomPlayer = SBChallenge.getRandomNumber(players83Plus.size());
//            temp.updateAtPos(randomInd, players83Plus.get(randomPlayer));
//
//            if (true) { //simulated annealing said accept
//                last = temp;
//            }
//
//        }






    }

    @Test
    void solver() {

        Squad s = Squad.createDefaultSquad();
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> allPlayers = pl.getAllPlayers();

        //initialize best cost as infinity
        double minCost = 1000000;
        double minRating = 87;
        double minChem = 70;
        Squad bestSquad = s;

        ///////////////// SQUAD PICKS

        Integer[] squadInds = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 2;
        Combinations c = new Combinations();
        c.combinations(squadInds, k, 0, new Integer[k]);
        ArrayList<Object[]> squadCombos = c.getCombos();

        ///////////////// PLAYER PICKS

        int eSize = pl.getAll86Plus().size();
        Integer[] playerInds = new Integer[eSize];

        for (int i=0; i<eSize; i++) {
            playerInds[i] = i;
        }

        Combinations c2 = new Combinations();
        c2.combinations(playerInds, k, 0, new Integer[k]);
        ArrayList<Object[]> playerCombos = c2.getCombos();

        Squad temp;
        for (int i=0; i<squadCombos.size(); i++) {
            for (int j=0; j<playerCombos.size(); j++) {
                temp = s;
                temp.updateAtPos((Integer)squadCombos.get(i)[0], allPlayers.get((Integer)playerCombos.get(j)[0]));
                temp.updateAtPos((Integer)squadCombos.get(i)[1], allPlayers.get((Integer)playerCombos.get(j)[1]));
                if (temp.getSquadRating() >= minRating && ChemistryEngine.calculateChemistry(temp) >= 70) {
                    if (temp.getSquadPrice() < minCost) {
                        System.out.println("PRICE: " + temp.getSquadPrice() + " CHEM: " + ChemistryEngine.calculateChemistry(temp) + " RATING: " + temp.getSquadRating());
                        temp.printSquad();
                        minCost = temp.getSquadPrice();
                        bestSquad = temp;
                    }
                }
            }
        }



//        Combinations.combinations(idx, k, 0, new Integer[k]);
        //get first n choose 2 indices
            //get every combination of 2 players, check chem/rating and and update min price if valid chem/rating and better min
        //get next n choose 2 indices
            //...


//        System.out.println(s.getSquadPrice());
//        System.out.println(ChemistryEngine.calculateChemistry(s));
//        System.out.println(s.getSquadRating());





    }

    @Test
    void getFitnessScore() {
    }

    @Test
    void getSquad() {
    }

    @Test
    void setSquad() {
    }

    @Test
    void getMinRating() {
    }

    @Test
    void setMinRating() {
    }

    @Test
    void getMinChem() {
    }

    @Test
    void setMinChem() {
    }
}