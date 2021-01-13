package jungsuk.ten;

import java.util.Calendar;

public class Ten_05 {

    public static int getDayDiff(String yyyymmdd1, String yyyymmdd2) {
        if (yyyymmdd1 == null || yyyymmdd2 == null) {
            return 0;
        }
        return 0;

    }

    public static void main(String[] args){
        System.out.println(getDayDiff("20010103","20010101"));
        System.out.println(getDayDiff("20010103","20010103"));
        System.out.println(getDayDiff("20010103","200103"));
    }

}
