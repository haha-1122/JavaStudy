import java.util.Scanner;

public class PhoneBookUI {

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

				System.out.println("����� �����ּ���");
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

				System.out.println("1~5 ������ ���ڸ� �Է��ϼ���.");
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
				System.out.println("���ڸ� �Է��� �ּ���");
				return false;
			}

		}
		return true;
	}


	private static void addMember() {
		if (sequence == repository.length) {
			System.out.println("����Ұ� ���� á���ϴ�.");
			return;
		}
		System.out.println("1. �Ϲ�");
		System.out.println("2. ����");
		System.out.println("3. ȸ��");
		char[] checkNum = sc.nextLine().trim().toCharArray();
		if (!isNumber(checkNum)) {
			addMember();
			return;
		} else if (checkNum.length != 1 || checkNum[0] < '1' || checkNum[0] > '3') {
			System.out.println("1~3������ ���ڸ� �Է����ּ���");
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
		System.out.print("�̸� : ");
		String name = sc.nextLine();
		boolean flag = true;
		for (int i = 0; i < sequence; i++) {
			if (repository[i].NAME.equals(name)) {
				System.out.println(repository[i] + "\n");
				flag = false;
			}
		}
		if (flag) {
			System.out.println("������ �����ϴ�.");
		}
	}

	private static void deleteMember() {
		System.out.println("��ȭ��ȣ : ");
		String PHONE_NUMBER = sc.nextLine();
		boolean flag = true;
		for (int i = 0; i < sequence; i++) {
			if (repository[i].PHONE_NUMBER.equals(PHONE_NUMBER)) {
				System.out.println("���� �����Ͻðڽ��ϱ�?  Y ����, N ó������");
				String str = sc.nextLine().toUpperCase().trim();
				if (str.equals(YES)) {
					for (int j = i; j < sequence-1; j++) {
						repository[j] = repository[j+1];
					}
					sequence--;
					flag = false;
					break;
				} else if (str.equals(NO)){
					System.out.println("ó������ ���ư��ϴ�.");
					flag = false;
					break;
				} else {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					i--;
				}
			}
		}
		if (flag) {
			System.out.println("������ �����ϴ�.");
		}
	}

	private static void showAll() {
		if (sequence == 0) {
			System.out.println("������ �����ϴ�.");
		}
		for (int i = 0; i < sequence; i++) {
			System.out.println(i);
			System.out.println(repository[i]);
			System.out.println();
		}
	}

	private static boolean isExit() {
		while (true) {
			System.out.println("�����Ͻðڽ��ϱ�? Y ����, N ó������");
			String str = sc.nextLine().trim().toUpperCase();
			if (str.equals(YES)) {
				System.out.println("�����մϴ�.");
				return true;
			} else if (str.equals(NO)) {
				return false;
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
		}
	}

	private static class Member implements Comparable<Member>{
		private final String NAME;
		private final String PHONE_NUMBER;
		private final String BIRTH;
		private static final Scanner sc = new Scanner(System.in);
		Member() {
			System.out.print("�̸� : ");
			NAME = sc.nextLine();
			System.out.print("��ȭ��ȣ : ");
			PHONE_NUMBER = sc.nextLine();
			System.out.print("���� : ");
			BIRTH = sc.nextLine();
		}
		@Override
		public String toString() {
			return  "�̸� : " + NAME +
					"\n��ȭ��ȣ : " + PHONE_NUMBER +
					"\n������� : " + BIRTH;

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
				System.out.print("�г� : ");
				arr = sc.nextLine().trim().toCharArray();
				if (isNumber(arr)) flag = false;
			}
			YEAR = toInteger(arr);

		}

		@Override
		public String toString() {
			return  super.toString() +
					"\n�г� : " + YEAR;
		}

	}

	private static class CompanyMember extends PhoneBook.Member {
		private final String COMPANY;

		CompanyMember() {
			super();
			System.out.print("ȸ�� : ");
			COMPANY = sc.nextLine();
		}

		@Override
		public String toString() {
			return  super.toString() +
					"\n���� : " + COMPANY;
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

	String PRINT_INTERFACE = "1. ������ �Է�" +
			"\n2. ������ �˻�" +
			"\n3. ������ ����" +
			"\n4. ��� ������ ����" +
			"\n5. ���α׷� ����";
}





