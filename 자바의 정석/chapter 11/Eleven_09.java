package jungsuk.eleven;

import java.util.*;

public class Eleven_09 {
    public static void calculateClassRank(List list) {
// . 먼저 반별 총점기준 내림차순으로 정렬한다
        Collections.sort(list, new ClassTotalComparator());
        int prevBan = -1;
        int prevRank = 0;
        int prevTotal = -1;
        int length = list.size();
        int count = 1;

        for (Object student : list) {
            if (student instanceof Student3) {
                if (((Student3) student).ban != prevBan) {
                    prevRank = 0;
                    prevTotal = -1;
                    count = 1;
                }
                if (((Student3) student).total == prevTotal) {
                    ((Student3) student).classRank = prevRank;
                    count++;
                }
                if (((Student3) student).total != prevTotal) {
                    prevRank += count;
                    count = 1;
                    ((Student3) student).classRank = prevRank;
                }
                prevBan = ((Student3) student).ban;
                prevRank = ((Student3) student).classRank;
                prevTotal = ((Student3) student).total;
            }
        }
    } // public static void calculateClassRank(List list) {

    public static void calculateSchoolRank(List list) {

        Collections.sort(list, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Student3)o2).total-((Student3)o1).total;
            }
        });

        int prevRank = 0;
        int prevTotal = -1;
        int count = 1;

        for (Object student : list) {
            if (student instanceof Student3) {
                if (((Student3) student).total != prevTotal) {
                    prevRank += count;
                    ((Student3) student).schoolRank = prevRank;
                    count = 1;
                }
                if (((Student3) student).total == prevTotal) {
                    ((Student3) student).schoolRank = prevRank;
                    count++;
                }
                prevTotal = ((Student3) student).total;
            }
        }


    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(new Student3("이자바", 2, 1, 70, 90, 70));

        list.add(new Student3("안자바", 2, 2, 60, 100, 80));

        list.add(new Student3("홍길동", 1, 3, 100, 100, 100));

        list.add(new Student3("남궁성", 1, 1, 90, 70, 80));

        list.add(new Student3("김자바", 1, 2, 80, 80, 90));
        calculateSchoolRank(list);

        calculateClassRank(list);

        Iterator it = list.iterator();
        while (it.hasNext())
            System.out.println(it.next());
    }


}


class Student3 implements Comparable {
    String name;
    int ban;
    int no;
    int kor;
    int eng;
    int math;
    int total;
    int schoolRank; // 전교등수
    int classRank; // 반등수

    Student3(String name, int ban, int no, int kor, int eng, int math) {
        this.name = name;
        this.ban = ban;
        this.no = no;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
        total = kor + eng + math;
    }

    int getTotal() {
        return total;
    }

    float getAverage() {
        return (int) ((getTotal() / 3f) * 10 + 0.5) / 10f;
    }

    public int compareTo(Object o) {
        if (o instanceof Student3) {
            Student3 tmp = (Student3) o;
            return tmp.total - this.total;
        } else {
            return -1;
        }
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
                + "," + schoolRank
                + "," + classRank // 새로추가
                ;
    }
} // class Student

class ClassTotalComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Student3 && o2 instanceof Student3) {
            if (((Student3) o1).ban == ((Student3) o2).ban) {
                return ((Student3) o2).total - ((Student3) o1).total;
            }else {
                return ((Student3) o1).ban - ((Student3) o2).ban;
            }

        }
        return 0;
    }
}
