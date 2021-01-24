package jungsuk.nine;

import java.util.Arrays;

public class Nine_10 {

    public static String format(String str, int length, int alignment) {
        StringBuffer sb = new StringBuffer(str);
        if (sb.length() > length) {
            sb.delete(length, sb.length());
            return sb.toString();
        }
        char[] arr = new char[length];
        Arrays.fill(arr, ' ');

        if (alignment == 0) {
            for (int i = 0; i < sb.length(); i++) {
                arr[i] = sb.charAt(i);
            }
            return new String(arr);
        }
        if (alignment == 1) {
            for (int i = (arr.length- sb.length())/2; i < (arr.length+sb.length())/2; i++) {
                arr[i]=sb.charAt(i-(arr.length-sb.length())/2);
            }
            return new String(arr);
        }

        if (alignment == 2) {
            for (int i = arr.length - sb.length(); i < arr.length; i++) {
                arr[i] = sb.charAt(i-arr.length + sb.length());
            }
            return new String(arr);
        }
        return "";
    }
    public static void main(String[] args) {
        String str = "가나다";

        System.out.println(format(str, 7, 0)); // 왼쪽 정렬
        System.out.println(format(str, 7, 1)); // 가운데 정렬
        System.out.println(format(str, 7, 2)); // 오른쪽 정렬
    }
}
