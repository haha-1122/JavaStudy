package jungsuk.eleven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Eleven_07 {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(new Student1("이자바 ",2,1,70,90,70));
        list.add(new Student1("안자바 ",2,2,60,100,80));
        list.add(new Student1("홍길동 ",1,3,100,100,100));
        list.add(new Student1("남궁성 ",1,1,90,70,80));
        list.add(new Student1("김자바 ",1,2,80,80,90));
        Collections.sort(list, new Comparator<Student1>() {
            @Override
            public int compare(Student1 s1, Student1 s2) {
                if (s1.ban != s2.ban) {
                    return Integer.compare(s1.ban, s2.ban);
                }
                return Integer.compare(s1.no, s2.no);
            }
        });
        Iterator it = list.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }
}

class Student1 {
    String name;
    int ban;
    int no;
    int kor;
    int eng;
    int math;

    Student1(String name, int ban, int no, int kor, int eng, int math) {
        this.name = name;
        this.ban = ban;
        this.no = no;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }

    int getTotal() {
        return kor + eng + math;
    }

    float getAverage() {
        return (int) ((getTotal() / 3f) * 10 + 0.5) / 10f;
    }

    public String toString() {
        return name
                + "," + ban
                + "," + no
                + "," + kor
                + "," + eng
                + "," + math
                + "," + getTotal()
                + "," + getAverage()
                ;
    }
} // class Student


