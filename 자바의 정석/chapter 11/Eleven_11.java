/*
package jungsuk.eleven;

import java.util.HashSet;

class SutdaCard {
    int num;
    boolean isKwang;
    SutdaCard() {
        this(1, true);
    }
    SutdaCard(int num, boolean isKwang) {
        this.num = num;
        this.isKwang = isKwang;
    }
    public boolean equals(Object obj) {
        if(obj instanceof SutdaCard1) {
            SutdaCard1 c = (SutdaCard1)obj;
            return num==c.num && isKwang==c.isKwang;
        } else {
            return false;
        }
    }
    public String toString() {
        return num + ( isKwang ? "K":"");
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
}

public class Eleven_11 {
    public static void main(String[] args) {
        SutdaCard1 c1 = new SutdaCard1(3,true);
        SutdaCard1 c2 = new SutdaCard1(3,true);
        SutdaCard1 c3 = new SutdaCard1(1,true);
        HashSet set = new HashSet();
        set.add(c1);
        set.add(c2);
        set.add(c3);
        System.out.println(set);
    }
}
*/
