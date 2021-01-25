package jungsuk.chapter_14;

import java.util.Arrays;
import java.util.stream.Stream;

public class C14_05 {
    public static void main(String[] args) {
        String[] strArr = {"aaa", "bb", "c", "dddd"};


        Arrays.asList(strArr).stream()
                .mapToInt(String::length)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);


        Arrays.asList(strArr).stream()
                .map(String::length)
                .reduce(Integer::max)
                .ifPresent(System.out::println);

        Stream.generate(() -> (int)(Math.random()*45+1))
                .distinct()
                .limit(6)
                .sorted()
                .forEach(System.out::print);
    }
}
