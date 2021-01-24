/*
package jungsuk.eleven;

import java.util.HashMap;

public class Eleven_12 {

    public static void main(String args[]) throws Exception {
        SutdaDeck3 deck = new SutdaDeck3();
        deck.shuffle();
        Player4 p1 = new Player4("타짜", deck.pick(), deck.pick());

        Player4 p2 = new Player4("고수", deck.pick(), deck.pick());

        System.out.println(p1 + " " + deck.getPoint(p1));
        System.out.println(p2 + " " + deck.getPoint(p2));
    }
}

class SutdaDeck {
    final int CARD_NUM = 20;
    SutdaCard1[] cards = new SutdaCard1[CARD_NUM];
    int pos = 0; // 다음에 가져올 카드의 위치
    HashMap jokbo = new HashMap(); // 족보를 저장할 HashMap

    SutdaDeck() {

        for (int i = 0; i < CARD_NUM; i++) {
            int num = i % 10 + 1;
            boolean isKwang = i < 10 && (num == 1 || num == 3 || num == 8);
            cards[i] = new SutdaCard1(num, isKwang);
        }
        registerJokbo(); // . 족보를 등록한다
    }

    void registerJokbo() {

        jokbo.put("KK", 4000);
        for (int i = 1; i <= 10; i++) {
            jokbo.put(i + "" + i, 3000 + i * 10);
        }
        jokbo.put("12", 2060);
        jokbo.put("21", 2060);
        jokbo.put("14", 2050);
        jokbo.put("41", 2050);
        jokbo.put("19", 2040);
        jokbo.put("91", 2040);
        jokbo.put("110", 2030);
        jokbo.put("101", 2030);
        jokbo.put("410", 2020);
        jokbo.put("104", 2020);
        jokbo.put("46", 2010);
        jokbo.put("64", 2010);
    }

    int getPoint(Player4 p) {
        if (p == null) return 0;
        SutdaCard1 c1 = p.c1;
        SutdaCard1 c2 = p.c2;
        Integer result = 0;
        boolean special = jokbo.containsKey(c1.num + "" + c2.num);
        if (c1.isKwang && c2.isKwang) {
            result = (int) jokbo.get("KK");
        } else if (special) {
            result = (int) jokbo.get(c1.num + "" + c2.num);
        } else
            result = (c1.num + c2.num) % 10 +1000;

        p.point = result;

        return result;
    }

    SutdaCard1 pick() throws Exception {
        SutdaCard1 c = null;
        if (0 <= pos && pos < CARD_NUM) {
            c = cards[pos];
            cards[pos++] = null;
        } else {
            throw new Exception("남아있는 카드가 없습니다.");

        }
        return c;
    }

    void shuffle() {
        for (int x = 0; x < CARD_NUM * 2; x++) {
            int i = (int) (Math.random() * CARD_NUM);
            int j = (int) (Math.random() * CARD_NUM);
            SutdaCard1 tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp;
        }
    }
} // SutdaDeck

class Player {
    String name;
    SutdaCard1 c1;
    SutdaCard1 c2;
    int point; // - 카드의 등급에 따른 점수 새로 추가

    Player(String name, SutdaCard1 c1, SutdaCard1 c2) {
        this.name = name;
        this.c1 = c1;
        this.c2 = c2;
    }

    public String toString() {
        return "[" + name + "]" + c1.toString() + "," + c2.toString();
    }
} // class Player

class SutdaCard1 {
    int num;
    boolean isKwang;

    SutdaCard1() {
        this(1, true);
    }

    SutdaCard1(int num, boolean isKwang) {
        this.num = num;
        this.isKwang = isKwang;
    }

    public String toString() {
        return num + (isKwang ? "K" : "");
    }
}
*/
