package jungsuk.eleven;

import java.util.ArrayList;
import java.util.Scanner;

public class Eleven_14 {
    static ArrayList record = new ArrayList(); // 성적데이터를 저장할 공간
    static Scanner s = new Scanner(System.in);
    public static void main(String args[]) {
        while(true) {
            switch(displayMenu()) {
                case 1 :
                    inputRecord();
                    break;
                case 2 :
                    displayRecord();
                    break;
                case 3 :
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
            }
        } // while(true)
    }
    // menu를 보여주는 메서드
    static int displayMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("**************************************************");
        System.out.println("*    성적 관리 프로그램    *");
        System.out.println("**************************************************");
        System.out.println();
        System.out.println(" 1. 학생성적 입력하기");
        System.out.println();
        System.out.println(" 2. 학생성적 보기");
        System.out.println();
        System.out.println(" 3. 프로그램 종료");
        System.out.println();

        int menu = 0;
        while (true) {
            System.out.print("원하는 메뉴를 선택하세요.(1~3) : ");
            menu = sc.nextInt();
            if (menu == 1 || menu == 2 || menu == 3) {
                break;
            } else {
                System.out.println("1~3까지의 숫자만 입력하세요");
            }

        }
        return menu;
    } // public static int displayMenu(){
// 데이터를 입력받는 메서드
static void inputRecord() {
    System.out.println("1. 학생성적 입력하기");
    System.out.println("이름,반,번호,국어성적,영어성적,수학성적'의 순서로 공백없이 입력하세요.");
    System.out.println("입력을 마치려면 q를 입력하세요. 메인화면으로 돌아갑니다.");
    while(true) {
        System.out.print(">>");
        Scanner sc = new Scanner(System.in).useDelimiter(",");
        String input = sc.nextLine();
        System.out.println(input);
        if (!input.equalsIgnoreCase("q")) {
            Scanner s2 = new Scanner(input).useDelimiter(",");
            System.out.println("dd");
            record.add(new Student(s2.next(), s2.nextInt(), s2.nextInt(), s2.nextInt(), s2.nextInt(), s2.nextInt()));
            System.out.println("입력완료");
        } else return;

    } // end of while
} // public static void inputRecord() {
    // 데이터 목록을 보여주는 메서드
    static void displayRecord() {
        int koreanTotal = 0;
        int englishTotal = 0;
        int mathTotal = 0;
        int total = 0;
        int length = record.size();
        if(length > 0) {
            System.out.println();
            System.out.println("이름 반 번호 국어 영어 수학 총점 평균 전교등수 반등수");
            System.out.println("====================================================");
            for (int i = 0; i < length ; i++) {
                Student student = (Student)record.get(i);
                System.out.println(student);
                koreanTotal += student.kor;
                mathTotal += student.math;
                englishTotal += student.eng;
                total += student.total;
            }
            System.out.println("====================================================");
            System.out.println("총점: "+koreanTotal+" "+englishTotal
                    +" "+mathTotal+" "+total);
            System.out.println();
        } else {
            System.out.println("====================================================");
            System.out.println("데이터가 없습니다.");
            System.out.println("====================================================");
        }
    } // static void displayRecord() {
}
class Student implements Comparable {
    String name;
    int ban;
    int no;
    int kor;
    int eng;
    int math;
    int total;
    int schoolRank;
    int classRank; // 반등수
    Student(String name, int ban, int no, int kor, int eng, int math) {
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
        if(o instanceof Student) {
            Student tmp = (Student)o;
            return tmp.total - this.total;
        } else {
            return -1;
        }
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
                +","+schoolRank
                +","+classRank
                ;
    }
} // class Student