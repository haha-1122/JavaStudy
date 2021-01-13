package jungsuk.nine;

public class Nine_4 {
    static void printGraph(int[] dataArr, char ch) {
        for (int a  : dataArr) {
            for (int i = 0; i < a; i++) {
                System.out.print(ch);
            }
            System.out.println(a);
        }
    }

    public static void main(String[] args) {

        printGraph(new int[] {3,7,1,4},'*');
    }
}
