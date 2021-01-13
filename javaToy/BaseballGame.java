
import java.util.Scanner;

public class BaseballGame {
	public static boolean check(StringBuffer sb, int cnt) {
		if (sb.length() != cnt) {
			System.out.printf("숫자 %d개를 입력하세요.\n",cnt);
			return false;
		}
		for (int i = 0; i < cnt; i++) {
			for (int j = i+1; j < cnt; j++) {
				if (sb.charAt(i) == sb.charAt(j)) {
					System.out.println("숫자는 중복될 수 없습니다.");
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args) {

		Gamer g1 = new Gamer();
		Gamer g2 = new Gamer();
		int count = 0;
		StringBuffer sb;
		Scanner sc = new Scanner(System.in);
		int numCount = 0;
		while(true) {
		
			System.out.print("몇개의 숫자를 가지고 하시겠습니까?");
			numCount = sc.nextInt();
			if ( numCount > 9 ) {

				System.out.println("숫자가 10자리 이상일 수 없습니다.");
				continue;
			} else if ( numCount <= 0 ) {
				
				System.out.println("숫자가 0 이하일 수 없습니다.");
				 continue;
			} 
			break;
		}
		
		sc.nextLine();
		g1.getProtect(numCount);
		System.out.println(g1.protect);
		while (true) {
			count++;
			sb = new StringBuffer(sc.nextLine());
			if (!check(sb,numCount)) {
				continue;
			}
			if (g2.attack(g1, sb)) {
				System.out.println("홈런");
				break;
			}
		}
		System.out.printf("%d번 만에 홈런을 치셨습니다",count);
	}
}

class Gamer {


	StringBuffer protect;
	Scanner sc;
	public boolean attack(Gamer g1, StringBuffer a) {
		int strike = 0;
		int ball = 0;

		for (int i = 0; i < g1.protect.length(); i++) {
			for (int j = 0; j < a.length(); j++) {
				if (i == j) {
					if (g1.protect.charAt(i) == a.charAt(j)) {
						strike++;
					}
				} else {
					if (g1.protect.charAt(i) == a.charAt(j)) {
						ball++;
					}
				}
			}
		}


		if (strike == 0 && ball == 0) {
			System.out.println("out");
			return false;
		}

		if (strike == g1.protect.length()) {
			return true;
		}
		System.out.printf("%d스트라이크 %d볼\n", strike, ball);
		return false;
	}

	public void getProtect(int cnt) {
		protect = new StringBuffer();
		boolean flag = true;
		sc = new Scanner(System.in);
		for (int i = 0; i < cnt; i++) {
			flag = true;
			int num = (int)(Math.random()*9+1);
			for (int j = 0; j < i; j++) {
				if ((char) num == protect.charAt(j)-'0') {
					i--;
					flag = false;
				}
			}
			if (flag) {
				protect.append(num);
			}
		}

	}



}
