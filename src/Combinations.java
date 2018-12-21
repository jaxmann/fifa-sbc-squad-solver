import java.util.Arrays;

public class Combinations {


//    public class Combination {
//        public static void main(String[] args){
//            String[] arr = {"A","B","C","D","E","F"};
//            combinations2(arr, 3, 0, new String[3]);
//        }

    static void combinations2(Object[] arr, int len, int startPosition, Object[] result){
        if (len == 0){
            System.out.println(Arrays.toString(result));
            return;
        }
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = arr[i];
            combinations2(arr, len-1, i+1, result);
        }
    }

}
