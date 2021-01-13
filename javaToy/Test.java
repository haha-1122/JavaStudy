import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		PhoneBook.run();

	}
}
class PhoneBook implements PhoneBookInterface{

	private static PhoneBook phoneBook;
	private static Member[] repository;
	private static int sequence;
	private static final Scanner sc = new Scanner(System.in);

	private PhoneBook(int size) {
		repository = new Member[size];
	}

	private static void getInstance() {

		if (phoneBook == null) {

			boolean flag = true;
			char[] temp = null;
			int size = 0;

			while (flag) {

				System.out.println("사이즈를 정해주세요");
				temp = sc.nextLine().trim().toCharArray();
				if (isNumber(temp)) {
					flag = false;
					size = toInteger(temp);

				}
			}

			phoneBook = new PhoneBook(size);

		}
	}

	public static void run() {

		getInstance();
		boolean flag = true;

		while (flag) {

			System.out.println(PRINT_INTERFACE);
			char[] temp = sc.nextLine().trim().toCharArray();

			if (!isNumber(temp)) {

				continue;

			} else if (temp.length != 1 || temp[0] < '0' || temp[0] > '5') {

				System.out.println("1~5 사이의 숫자만 입력하세요.");
				continue;

			}

			int select = toInteger(temp);
			switch (select) {

				case DATA_INPUT :
					addMember();
					break;
				case DATA_SEARCH:
					searchMember();
					break;
				case DATA_DELETE:
					deleteMember();
					break;
				case SHOW_ALL_DATA:
					showAll();
					break;
				case EXIT:
					flag = !isExit();
					break;

			}
		}
	}

	private static int toInteger(char[] arr) {

		StringBuffer sb = new StringBuffer();

		for (char ch : arr) {

			sb.append(ch - '0');

		}

		return Integer.parseInt(sb.toString());

	}

	private static boolean isNumber(char[] arr) {
		if (arr.length == 0) {

			return false;

		}
		for (char ch : arr) {

			if (ch < '0' || ch > '9') {
				System.out.println("숫자만 입력해 주세요");
				return false;
			}

		}
		return true;
	}


	private static void addMember() {
		if (sequence == repository.length) {
			System.out.println("저장소가 가득 찼습니다.");
			return;
		}
		System.out.println("1. 일반");
		System.out.println("2. 대학");
		System.out.println("3. 회사");
		char[] checkNum = sc.nextLine().trim().toCharArray();
		if (!isNumber(checkNum)) {
			addMember();
			return;
		} else if (checkNum.length != 1 || checkNum[0] < '1' || checkNum[0] > '3') {
			System.out.println("1~3까지의 숫자만 입력해주세요");
			addMember();
			return;
		}
		int select = checkNum[0]-'0';
		switch (select) {
			case GENERAL:
				sort(new Member());
				break;
			case UNIV:
				sort(new UnivMember());
				break;
			case COMPANY:
				sort(new CompanyMember());
		}
	}

	private static void sort(Member member) {
		boolean flag = true;
		if (sequence == 0) {
			repository[sequence++] = member;
			return;
		}
		for (int i = 0; i < sequence; i++) {
			if (repository[i].compareTo(member) > 0) {
				for (int j = sequence; j > i; j--) {
					repository[j] = repository[j-1];
				}
				repository[i] = member;
				sequence++;
				flag = false;
				break;
			}
		}
		if (flag) {
			repository[sequence++] = member;
		}
	}

	private static void searchMember() {
		System.out.print("이름 : ");
		String name = sc.nextLine();
		boolean flag = true;
		for (int i = 0; i < sequence; i++) {
			if (repository[i].NAME.equals(name)) {
				System.out.println(repository[i] + "\n");
				flag = false;
			}
		}
		if (flag) {
			System.out.println("정보가 없습니다.");
		}
	}

	private static void deleteMember() {
		System.out.println("전화번호 : ");
		String PHONE_NUMBER = sc.nextLine();
		boolean flag = true;
		for (int i = 0; i < sequence; i++) {
			if (repository[i].PHONE_NUMBER.equals(PHONE_NUMBER)) {
				System.out.println("정말 삭제하시겠습니까?  Y 삭제, N 처음으로");
				String str = sc.nextLine().toUpperCase().trim();
				if (str.equals(YES)) {
					for (int j = i; j < sequence-1; j++) {
						repository[j] = repository[j+1];
					}
					sequence--;
					flag = false;
					break;
				} else if (str.equals(NO)){
					System.out.println("처음으로 돌아갑니다.");
					flag = false;
					break;
				} else {
					System.out.println("잘못 입력하셨습니다.");
					i--;
				}
			}
		}
		if (flag) {
			System.out.println("정보가 없습니다.");
		}
	}

	private static void showAll() {
		if (sequence == 0) {
			System.out.println("정보가 없습니다.");
		}
		for (int i = 0; i < sequence; i++) {
			System.out.println(i);
			System.out.println(repository[i]);
			System.out.println();
		}
	}

	private static boolean isExit() {
		while (true) {
			System.out.println("종료하시겠습니까? Y 종료, N 처음으로");
			String str = sc.nextLine().trim().toUpperCase();
			if (str.equals(YES)) {
				System.out.println("종료합니다.");
				return true;
			} else if (str.equals(NO)) {
				return false;
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}

	private static class Member implements Comparable<Member>{
		private final String NAME;
		private final String PHONE_NUMBER;
		private final String BIRTH;
		private static final Scanner sc = new Scanner(System.in);
		Member() {
			System.out.print("이름 : ");
			NAME = sc.nextLine();
			System.out.print("전화번호 : ");
			PHONE_NUMBER = sc.nextLine();
			System.out.print("생일 : ");
			BIRTH = sc.nextLine();
		}
		@Override
		public String toString() {
			return  "이름 : " + NAME +
					"\n전화번호 : " + PHONE_NUMBER +
					"\n생년월일 : " + BIRTH;

		}

		@Override
		public int compareTo(Member member) {
				return this.NAME.compareTo(member.NAME);
		}

	}

	private static class UnivMember extends PhoneBook.Member {
		private final int YEAR;

		UnivMember() {
			super();
			boolean flag = true;
			char[] arr = null;
			while (flag) {
				System.out.print("학년 : ");
				arr = sc.nextLine().trim().toCharArray();
				if (isNumber(arr)) flag = false;
			}
			YEAR = toInteger(arr);

		}

		@Override
		public String toString() {
			return  super.toString() +
					"\n학년 : " + YEAR;
		}

	}

	private static class CompanyMember extends PhoneBook.Member {
		private final String COMPANY;

		CompanyMember() {
			super();
			System.out.print("회사 : ");
			COMPANY = sc.nextLine();
		}

		@Override
		public String toString() {
			return  super.toString() +
					"\n직장 : " + COMPANY;
		}


	}

}

interface PhoneBookInterface {
	int DATA_INPUT = 1;
	int DATA_SEARCH = 2;
	int DATA_DELETE = 3;
	int SHOW_ALL_DATA = 4;
	int EXIT = 5;

	int GENERAL = 1;
	int UNIV = 2;
	int COMPANY = 3;

	String YES = "Y";
	String NO = "N";

	String PRINT_INTERFACE = "1. 데이터 입력" +
			"\n2. 데이터 검색" +
			"\n3. 데이터 삭제" +
			"\n4. 모든 데이터 보기" +
			"\n5. 프로그램 종료";
}





