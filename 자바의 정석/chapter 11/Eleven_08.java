package jungsuk.eleven;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Eleven_08 {
    public static void calculateSchoolRank(List list) {
        Collections.sort(list); // list . 먼저 를 총점기준 내림차순으로 정렬한다
        int prevRank = 0; // 이전 전교등수
        int prevTotal = -1; // 이전 총점
        int length = list.size();
        int count = 1;

        for (Object students : list) {
            if (((Student2) students).total == prevTotal) {
                ((Student2) students).schoolRank = prevRank;
                count++;
            }
            if (((Student2) students).total != prevTotal) {
                prevRank += count;
                ((Student2) students).schoolRank = prevRank;
                count = 1;
            }
            prevTotal = ((Student2) students).total;
        }
    }
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(new Student2("이자바 ",2,1,70,90,70));
        list.add(new Student2("안자바 ",2,2,60,100,80));
        list.add(new Student2("홍길동 ",1,3,100,100,100));
        list.add(new Student2("남궁성 ",1,1,90,70,80));
        list.add(new Student2("김자바 ",1,2,80,80,90));
        calculateSchoolRank(list);
        Iterator it = list.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
}


class Student2 implements Comparable {
    String name;
    int ban;
    int no;
    int kor;
    int eng;
    int math;
    int total; // 총점
    int schoolRank; // 전교등수
    Student2(String name, int ban, int no, int kor, int eng, int math) {
        this.name = name;
        this.ban = ban;
        this.no = no;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
        total = kor+eng+math;
    }
    int getTotal() {
        return total;
    }
    float getAverage() {
        return (int)((getTotal()/ 3f)*10+0.5)/10f;
    }
    public int compareTo(Object o) {

        if (o instanceof Student2) {
            Student2 student = (Student2) o;
            return this.total > student.total ? -1 : this.total < student.total ? 1 : 0;
        }
        return 0;

    }
    public String toString() {
        return name
                +","+ban
                +","+no
                +","+kor
                +","+eng
                +","+math
                +","+getTotal()
                +","+getAverage()
                +","+schoolRank; // 새로추가
    }
} // class Student
