import java.util.Arrays;

public class Test {
	public static String fillZero(String src, int length) {
		if (src == null || src.length() == length) {
			return src;
		}
		if (length < 1) {
			return "";
		}
		if (src.length() > length) {
			return src.substring(0, length);
		}
		char[] arr = new char[length];
		Arrays.fill(arr, '0');
		char[] arr2 = src.toCharArray();
		System.arraycopy(arr2, 0, arr, arr.length-arr2.length, arr2.length);
		return new String(arr);
	}


	public static void main(String[] args) {
		String src = "12345";
		System.out.println(fillZero(src,10));
		System.out.println(fillZero(src,-1));
		System.out.println(fillZero(src,3));
	}

}







