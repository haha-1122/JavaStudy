package jungsuk.nine;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Nine_1 {
    public static void main(String[] args) {

        SutdaCard c1 = new SutdaCard(3,true);
        SutdaCard c2 = new SutdaCard(3,true);
        System.out.println("c1="+c1);
        System.out.println("c2="+c2);
        System.out.println("c1.equals(c2):"+c1.equals(c2));
        System.out.println(c1.getClass());
    }

}
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
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SutdaCard that = (SutdaCard) obj;
        if (this.isKwang == that.isKwang && this.num == that.num) {
            return true;
        }
        return false;



    }
    public String toString() {
        return num + ( isKwang ? "K":"");
    }
}


