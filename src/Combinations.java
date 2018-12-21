import java.util.ArrayList;
import java.util.Arrays;

public class Combinations {


    public ArrayList<Object[]> combos;

    public Combinations() {
        this.combos = new ArrayList<>();
    }


    public void combinations(Object[] arr, int len, int startPosition, Object[] result) {
        if (len == 0) {
            this.combos.add(result);
//            System.out.println(Arrays.toString(result));
            return;
        }
        for (int i = startPosition; i <= arr.length - len; i++) {
            result[result.length - len] = arr[i];
            combinations(arr, len - 1, i + 1, result.clone());
        }

    }

    public ArrayList<Object[]> getCombos() {
        return this.combos;
    }

}
