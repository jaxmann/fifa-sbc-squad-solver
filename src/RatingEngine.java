import java.util.ArrayList;

public class RatingEngine {

    private int numPlayers;

    public RatingEngine(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public static double getRating(Squad squad) {

        ArrayList<Integer> ratings = squad.getRatings();

        double playerSum = 0;
        for (int r : ratings) {
            playerSum += r;
        }

        double avg = playerSum / 11;

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
