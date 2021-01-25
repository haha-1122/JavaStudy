package jungsuk.chapter_14;

import java.util.Arrays;
import java.util.stream.IntStream;

public class C14_04 {
    public static void main(String[] args) {
        IntStream.rangeClosed(1,6).boxed()
                .flatMap(x -> IntStream.rangeClosed(1,6).boxed().map(y -> new int[]{x,y}))
                .filter(x -> x[0] + x[1] ==6)
                .map(Arrays::toString)
                .forEach(System.out::println);





    }
}
