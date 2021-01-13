package jungsuk.nine;

public class Nine_13 {

    public static void main(String[] args) {
        String src = "aabbccAABBCCaa";
        System.out.println(src);
        System.out.println("aa " + stringCount(src, "aa") + "를 개 찾았습니다.");
    }

    static int stringCount(String src, String key) {
        return stringCount(src, key, 0);
    }

    static int stringCount(String src, String key, int pos) {
        int count = 0;
        int index = 0;
        if (key == null || key.length() == 0)
            return 0;
        StringBuffer sb = new StringBuffer(src);
        for (int i = 0; i < src.length(); i++) {
            if (sb.indexOf(key) != -1) {
                sb.delete(sb.indexOf(key),sb.indexOf(key)+key.length());
                count++;
            }
        }
        return count;
    }
}


