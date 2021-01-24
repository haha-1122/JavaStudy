package jungsuk.nine;

import java.util.Scanner;

public class Nine_11 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int min = sc.nextInt();
        int max = sc.nextInt();
        for (int i = min; i <= max; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.printf("%d*%d=%d\n",i,j,i*j);
            }
            System.out.println();
        }
    }
}
