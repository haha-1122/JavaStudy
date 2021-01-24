package jungsuk.ten;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Ten_01 {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd은 2번째 일요일입니다.");
        calendar.set(2010, 0, 10, 0, 0, 0);
        for (int i = 0; i < 52; i += 2) {
            System.out.println(format.format(calendar.getTime()));
            calendar.add(Calendar.WEEK_OF_MONTH, 2);
        }



    }
}
