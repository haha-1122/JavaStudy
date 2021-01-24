import java.util.Arrays;

public class Chapter_09_04 {
	static void printGraph(int[] dataArr, char ch) {
		Arrays.stream(dataArr).forEach(x -> {
			for (int i = 0; i < x; i++) {
				System.out.print(ch);
			}
			System.out.println(x);
		});
	}
	public static void main(String[] args) {
		printGraph(new int[]{3,7,1,4},'*');
	}

}







