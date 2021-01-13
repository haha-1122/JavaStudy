package jungsuk.nine;

public class Nine_6 {
    public static String fillZero(String src, int length) {

        if (src == null || src.length() == length) {
            return src;
        }
        if (length <= 0 ) {
            return "";
        }
        if (src.length() > length) {
            return new StringBuilder(src).delete(length,src.length()).toString();
        }
        char[] arr = new char[length];
        for (int i = 0; i < arr.length - src.length(); i++) {
            arr[i] = '0';
        }
        for (int i = arr.length - src.length(); i < arr.length; i++) {
            arr[i] = src.charAt(i-arr.length+src.length());
        }

        src = new String(arr);
        return src;
    }
    public static void main(String[] args) {
        String src = "12345";
        System.out.println(fillZero(src,10));
        System.out.println(fillZero(src,-1));
        System.out.println(fillZero(src,3));
    }
}

