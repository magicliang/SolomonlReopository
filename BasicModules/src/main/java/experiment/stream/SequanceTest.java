package experiment.stream;

import java.util.stream.IntStream;

/**
 * @author liangchuan
 */
public class SequanceTest {
    public static void main(String[] args) {
        /**
         * 注意顺序
         * 0
         * 0
         * 1
         * 1
         * 2
         * 2
         * 3
         * 3
         * 4
         * 4
         */
        IntStream.range(0, 5).map((x)-> {
            System.out.println(x);
            return x;
        }).map((x)-> {
            System.out.println(x);
            return x;
        }).average().getAsDouble();
    }
}
