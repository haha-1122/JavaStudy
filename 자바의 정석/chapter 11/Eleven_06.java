package jungsuk.eleven;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class Eleven_06 {
    static int getGroupCount(TreeSet tset, int from, int to) {
        Students s1 = new Students("", 0,0, to, to, to);
        Students s2 = new Students("", 0, 0, from, from, from);
        return tset.subSet(s2, s1).size();

    }
    public static void main(String[] args) {
        TreeSet set = new TreeSet(new Comparator() {
            public int compare(Object o1, Object o2) {

                if (o1 instanceof Students && o2 instanceof Students) {
                    Students s1 = (Students) o1;
                    Students s2 = (Students) o2;

                    return Float.compare(s1.getAverage(), s2.getAverage());
                }
                else return 0;
            }
        });


        set.add(new Students("홍길동 ",1,1,100,100,100));
        set.add(new Students("남궁성 ",1,2,90,70,80));
        set.add(new Students("김자바 ",1,3,80,80,90));
        set.add(new Students("이자바 ",1,4,70,90,70));
        set.add(new Students("안자바 ",1,5,60,100,80));
        Iterator it = set.iterator();
        while(it.hasNext())
            System.out.println(it.next());
        System.out.println("[60~69] :"+getGroupCount(set,60,70));
        System.out.println("[70~79] :"+getGroupCount(set,70,80));
        System.out.println("[80~89] :"+getGroupCount(set,80,90));
        System.out.println("[90~100] :"+getGroupCount(set,90,101));
    }

}
class Students implements Comparable {
    String name;
    int ban;
    int no;

    int kor;
    int eng;
    int math;
    Students(String name, int ban, int no, int kor, int eng, int math) {
        this.name = name;
        this.ban = ban;
        this.no = no;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }
    int getTotal() {
        return kor+eng+math;
    }
    float getAverage() {
        return (int)((getTotal()/ 3f)*10+0.5)/10f;
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
                ;
    }
    public int compareTo(Object o) {
        if(o instanceof Students) {
            Students tmp = (Students)o;
            return name.compareTo(tmp.name);
        } else {
            return -1;
        }
    }
} // class Student

