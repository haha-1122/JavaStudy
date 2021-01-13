package jungsuk.eleven;

import java.util.*;

public class Eleven_10 {

    public static void main(String[] args) {
        Set set = new HashSet(); // 중복제거 목적, HashSet -> ?? 얘 뭐임?
        int[][] board = new int[5][5];
        for(int i=0; set.size() < 25; i++) { //TreeSet이 아닌데 자동으로 정렬된다
            set.add((int)(Math.random()*30)+1+"");
        }
        ArrayList list = new ArrayList(set);
        Collections.shuffle(list);

        Iterator it = set.iterator();
        for(int i=0; i < board.length; i++) {
            for(int j=0; j < board[i].length; j++) {
                board[i][j] = Integer.parseInt((String)it.next());
                System.out.print((board[i][j] < 10 ? "\t" : "\t")
                        + board[i][j]);
            }
            System.out.println();
        }
    }
}
