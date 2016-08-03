import java.util.Random;

/**
 * Created by magicliang on 2016/8/3.
 */
public class ShuffleTest {
    private static Random random = new Random();

    public static void main(String[] args){
        int[] array = {9,2,3,4};
        shuffle(array);
        System.out.println(array);
   }

    private static void shuffle(int[] array){
        //don't shuffle the first element
        for(int i = array.length - 1; i > 0; i--){
            int j = random.nextInt(i+1);//Use an upper bound.
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }
}
