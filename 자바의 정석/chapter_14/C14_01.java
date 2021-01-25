package jungsuk.chapter_14;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class C14_01 {
    public static void main(String[] args) {
        BiFunction<Integer,Integer,Integer> f1 = Math::max;
        BiConsumer<String, Integer> f2 = (name, i) -> System.out.println(name + "=" + i);
        Function<Integer, Integer> f3 = x -> x*x;
        Supplier<Integer> f4 = () -> (int) (Math.random() * 6);
        Function<Integer[], Integer> f5 = arr -> {
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                sum += i;
            }
            return sum;
        };
        Supplier<int[]> f6 = () -> new int[]{};

        /*
        * String::length;
        * 변환불가
        * Arrays::stream;
        * String::equals;
        * Integer::compare;
        * Card::new;
        * System.out::println;
        * Math::random;
        * String::toUpperCase;
        * NullPointerException::new;
        * Optional::get;
        * StringBuffer::append;
        * System.out::println
        * */

        /*
        * 14-3
        * b
        * */

    }
}
