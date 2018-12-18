import java.util.ArrayList;

public class RatingEngine {

    private int numPlayers;

    public RatingEngine(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public static double getRating(ArrayList<Integer> ratings) {

        double playerSum = 0;
        for (int r : ratings) {
            playerSum += r;
        }

//        System.out.println(playerSum);

        double avg = playerSum / 11;

//        System.out.println(avg);

        double totExcess = 0.0;
        for (int r : ratings) {
            if (r >= avg) {
                totExcess += (r - avg);
            }
        }

        double teamTot = Math.round(playerSum + totExcess) / 11;

        double finalRating = Math.floor(teamTot);

        return finalRating;

    }


}
