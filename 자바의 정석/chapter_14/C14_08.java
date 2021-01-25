/*
package jungsuk.chapter_14;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class C14_08 {
    public static void main(String[] args) {
        Student[] stuArr = {
                new Student("나자바", true, 1, 1, 300),
                new Student("김지미", false, 1, 1, 250),
                new Student("김자바", true, 1, 1, 200),
                new Student("이지미", false, 1, 2, 150),
                new Student("남자바", true, 1, 2, 100),
                new Student("안지미", false, 1, 2, 50),
                new Student("황지미", false, 1, 3, 100),
                new Student("강지미", false, 1, 3, 150),
                new Student("이자바", true, 1, 3, 200),
                new Student("나자바", true, 2, 1, 300),
                new Student("김지미", false, 2, 1, 250),
                new Student("김자바", true, 2, 1, 200),
                new Student("이지미", false, 2, 2, 150),
                new Student("남자바", true, 2, 2, 100),
                new Student("안지미", false, 2, 2, 50),
                new Student("황지미", false, 2, 3, 100),
                new Student("강지미", false, 2, 3, 150),
                new Student("이자바", true, 2, 3, 200)
        };
        Map<Boolean, Map<Boolean, Long>> failedStuBySex = Stream.of(stuArr)
                .collect(Collectors.partitioningBy(Student::isMale,Collectors.partitioningBy(x -> x.getScore() < 150,Collectors.counting())));
        long failedMaleStuNum = failedStuBySex.get(true).get(true);
        long failedFemaleStuNum = failedStuBySex.get(false).get(true);
        System.out.println("불합격[남자]:"+ failedMaleStuNum +"명");
        System.out.println("불합격[여자]:"+ failedFemaleStuNum +"명");

    }
}
class Student {
    String name;
    boolean isMale; // 성별
    int hak; // 학년
    int ban; // 반
    int score;
    Student(String name, boolean isMale, int hak, int ban, int score) {
        this.name = name;
        this.isMale = isMale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }
    String getName() { return name;}
    boolean isMale() { return isMale;}
    int getHak() { return hak;}
    int getBan() { return ban;}
    int getScore() { return score;}
    public String toString() {
        return String.format("[%s, %s, %d학년 %d반 , %3d점 ]", name, isMale ?
                "남":"여", hak, ban, score);
    }
    // groupingBy()에서 사용
    enum Level { HIGH, MID, LOW } // , , 성적을 상 중 하 세 단계로 분류
}
*/
