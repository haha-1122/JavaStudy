package jungsuk.ten;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Ten_04 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String date;
        while (true) {
            System.out.print("yyyy/MM/dd");
            date = sc.nextLine().trim();
            if (!date.contains("/") || date.length() != 10) {
                break;
            }
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month+1, day);
        SimpleDateFormat sdf = new SimpleDateFormat("E요일입니다.");

        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(sdf.format(calendar.getTime()));


    }
}


