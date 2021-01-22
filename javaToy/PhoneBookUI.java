import java.util.*;

public class PhoneBookUI {
	public static void main(String[] args) {
		PhoneBook.getInstance().run();
	}
}

class PhoneBook {
	private PhoneBook() {
	}

	private final ArrayList<PhoneInfo> repository = new ArrayList<>(100);
	private static final Scanner sc = new Scanner(System.in);

	public static MenuChoice inputNum(String option) { // 예외처리 메소드

		try {
			int num = sc.nextInt();
			sc.nextLine();
			if (num < 1)
				MenuChoice.valueOf(num, option);
			return MenuChoice.valueOf(num, option);
		} catch (MenuChoiceException e) {
			System.out.println(e.getMessage());
			return MenuChoice.ERROR;
		} catch (InputMismatchException e) {
			System.out.println("잘못 입력하셨습니다.");
			sc.nextLine();
			return MenuChoice.ERROR;
		}
	}

	public void insertSort(PhoneInfo p) { // 삽입정렬
		if (repository.isEmpty()) {
			repository.add(p);
			return;
		}
		for (int i = 0; i < repository.size(); i++) {
			if (p.NAME.compareTo(repository.get(i).NAME) < 0) {
				repository.add(i, p);
				return;
			}
		}
		repository.add(p);
	}

	public void run() {
		Loop1:
		while (true) {
			System.out.print(MenuChoice.PRINT_CHOICE.MESSAGE);
			MenuChoice choice = inputNum("CHOICE");
			switch (choice) {
				case ADD:
					add();
					break;
				case SEARCH:
					search();
					break;
				case DELETE:
					delete();
					break;
				case SHOW_ALL:
					showAll();
					break;
				case EXIT:
					if (exit()) {
						sc.close();
						break Loop1;
					}
			}
		}
	}


	private void add() { // 입력
		System.out.print(MenuChoice.PRINT_ADD.MESSAGE);
		MenuChoice choice = inputNum("ADD");
		switch (choice) {
			case GENERAL:
				insertSort(new PhoneInfo());
				break;
			case UNIV:
				try { // 예외처리
					insertSort(new PhoneUnivInfo());
				} catch (InputMismatchException e) {
					break;
				}
				break;
			case COMP:
				insertSort(new PhoneCompanyInfo());
				break;
		}
	}

	private void search() { // 검색
		if (repository.isEmpty()) {
			System.out.println("데이터가 존재하지 않습니다.");
			return;
		}
		System.out.println("검색을 시작합니다.");
		System.out.print("이름 : ");
		String name = sc.nextLine().trim();
		boolean flag = true;
		for (PhoneInfo p : repository) {
			if (p.NAME.equals(name)) {
				System.out.println(p);
				flag = false;
			}
		}
		if (flag)
			System.out.println("해당하는 값이 존재하지 않습니다.");
	}

	private void delete() { // 삭제
		if (repository.isEmpty()) {
			System.out.println("데이터가 존재하지 않습니다.");
			return;
		}
		System.out.println("삭제를 시작합니다");
		System.out.print("전화번호 : ");
		String phoneNumber = sc.nextLine();
		for (PhoneInfo p1 : repository) {
			if (p1.PHONE_NUMBER.equals(phoneNumber)) {
				System.out.print("정말 삭제하시겠습니까? 삭제: Y, 취소 N\n" +
						">> ");
				String select = sc.nextLine().trim().toUpperCase();
				switch (select) {
					case "Y":
						repository.remove(p1);
						System.out.println("삭제 완료");
						return;
					case "N":
						System.out.println("처음으로 돌아갑니다.");
						return;
					default:
						System.out.println("잘못 입력하셨습니다.\n" +
								"처음으로 돌아갑니다.");
						return;
				}
			}
		}
		System.out.println("정보가 없습니다.");
	}

	private void showAll() { // 모든 결과
		if (repository.isEmpty()) {
			System.out.println("데이터가 존재하지 않습니다.");
			return;
		}
		repository.forEach(System.out::println);
	}

	private boolean exit() { // 종료
		System.out.print("종료 하시겠습니까? 종료: Y, 취소: N\n" +
				">> ");
		String select = sc.nextLine().trim().toUpperCase();
		switch (select) {
			case "Y":
				System.out.println("종료합니다.");
				return true;
			case "N":
				System.out.println("처음으로 돌아갑니다.");
				return false;
			default:
				System.out.println("잘못 입력하셨습니다.\n" +
						"처음으로 돌아갑니다.");
				return false;
		}
	}

	private static class Singleton {
		private static final PhoneBook singleton = new PhoneBook();
	}

	public static PhoneBook getInstance() {
		return Singleton.singleton;
	}

	private class PhoneInfo {
		final String NAME;
		final String PHONE_NUMBER;

		PhoneInfo(String NAME, String PHONE_NUMBER) {
			this.NAME = NAME;
			this.PHONE_NUMBER = PHONE_NUMBER;
		}

		PhoneInfo() {
			System.out.print("이름 : ");
			this.NAME = sc.nextLine();
			System.out.print("전화번호 : ");
			this.PHONE_NUMBER = sc.nextLine();
		}

		@Override
		public String toString() {
			return String.format("\n" +
							"이름 : %s\n" +
							"전화번호 : %s",
					NAME, PHONE_NUMBER
			);
		}
	}

	private class PhoneUnivInfo extends PhoneInfo {
		final String MAJOR;
		final int YEAR;

		PhoneUnivInfo(String NAME, String PHONE_NUMBER, String MAJOR, int YEAR) {
			super(NAME, PHONE_NUMBER);
			this.MAJOR = MAJOR;
			this.YEAR = YEAR;
		}

		PhoneUnivInfo() {
			System.out.print("전공 : ");
			this.MAJOR = sc.nextLine();
			try {
				System.out.print("학년 : ");
				this.YEAR = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("잘못 입력하셨습니다.");
				throw new InputMismatchException();
			}
		}

		@Override
		public String toString() {
			return super.toString() + String.format("\n" +
					"전공 : %s\n" +
					"학년 : %d",
					MAJOR, YEAR
			);
		}
	}

	private class PhoneCompanyInfo extends PhoneInfo {
		final String COMPANY;

		PhoneCompanyInfo(String NAME, String PHONE_NUMBER, String COMPANY) {
			super(NAME, PHONE_NUMBER);
			this.COMPANY = COMPANY;
		}

		PhoneCompanyInfo() {
			System.out.print("회사 : ");
			this.COMPANY = sc.nextLine();
		}
		@Override
		public String toString() {
			return super.toString() + String.format(
					"\n회사 : %s",COMPANY
			);
		}
	}

	private enum MenuChoice {
		PRINT_CHOICE("\n" +
				"선택하세요...\n" +
				"1. 데이터 입력\n" +
				"2. 데이터 검색\n" +
				"3. 데이터 삭제\n" +
				"4. 모든 데이터 보기\n" +
				"5. 프로그램 종료\n" +
				"선택 : "
		),
		PRINT_ADD("\n" +
				"데이터 입력을 시작합니다\n" +
				"1. 일반, 2. 대학, 3. 회사\n" +
				"선택 >> "
		),

		ADD("CHOICE", 1),
		SEARCH("CHOICE", 2),
		DELETE("CHOICE", 3),
		SHOW_ALL("CHOICE", 4),
		EXIT("CHOICE", 5),

		GENERAL("ADD", 1),
		UNIV("ADD", 2),
		COMP("ADD", 3),

		ERROR("ERROR");

		private final int NUM;
		private final String MESSAGE;

		MenuChoice(String MESSAGE, int NUM) {
			this.MESSAGE = MESSAGE;
			this.NUM = NUM;
		}

		MenuChoice(int NUM) {
			this("", NUM);
		}

		MenuChoice(String MESSAGE) {
			this(MESSAGE, -1);
		}

		private static MenuChoice valueOf(int num, String option) { // enum 예외처리 
			return Arrays.stream(MenuChoice.values())
					.filter(x -> x.NUM == num)
					.filter(x -> x.MESSAGE.equals(option))
					.findAny()
					.orElseThrow(() -> new MenuChoiceException(num));
		}

	}
}

class MenuChoiceException extends RuntimeException { // 예외처리 클래스
	private final int ERR_NUM;

	MenuChoiceException(String Message, int ERR_NUM) {
		super(Message);
		this.ERR_NUM = ERR_NUM;
	}

	MenuChoiceException(int ERR_NUM) {
		this("에 해당하는 선택은 존재하지 않습니다.", ERR_NUM);
	}

	@Override
	public String getMessage() {
		return ERR_NUM + super.getMessage();
	}
}
