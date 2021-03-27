package player;

import java.io.Serializable;
import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>, Serializable {

    @Override
    public int compare(Player p1, Player p2) {
        // should put lowest prices at top of Min Heap
        if (p1.getPrice() > p2.getPrice()) {
            return 1;
        } else if (p1.getPrice() < p2.getPrice()) {
            return -1;
        }
        return 0;
    }
}
