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

	public static MenuChoice inputNum(String option) { // ����ó�� �޼ҵ�

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
			System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			sc.nextLine();
			return MenuChoice.ERROR;
		}
	}

	public void insertSort(PhoneInfo p) { // ��������
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


	private void add() { // �Է�
		System.out.print(MenuChoice.PRINT_ADD.MESSAGE);
		MenuChoice choice = inputNum("ADD");
		switch (choice) {
			case GENERAL:
				insertSort(new PhoneInfo());
				break;
			case UNIV:
				try { // ����ó��
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

	private void search() { // �˻�
		if (repository.isEmpty()) {
			System.out.println("�����Ͱ� �������� �ʽ��ϴ�.");
			return;
		}
		System.out.println("�˻��� �����մϴ�.");
		System.out.print("�̸� : ");
		String name = sc.nextLine().trim();
		boolean flag = true;
		for (PhoneInfo p : repository) {
			if (p.NAME.equals(name)) {
				System.out.println(p);
				flag = false;
			}
		}
		if (flag)
			System.out.println("�ش��ϴ� ���� �������� �ʽ��ϴ�.");
	}

	private void delete() { // ����
		if (repository.isEmpty()) {
			System.out.println("�����Ͱ� �������� �ʽ��ϴ�.");
			return;
		}
		System.out.println("������ �����մϴ�");
		System.out.print("��ȭ��ȣ : ");
		String phoneNumber = sc.nextLine();
		for (PhoneInfo p1 : repository) {
			if (p1.PHONE_NUMBER.equals(phoneNumber)) {
				System.out.print("���� �����Ͻðڽ��ϱ�? ����: Y, ��� N\n" +
						">> ");
				String select = sc.nextLine().trim().toUpperCase();
				switch (select) {
					case "Y":
						repository.remove(p1);
						System.out.println("���� �Ϸ�");
						return;
					case "N":
						System.out.println("ó������ ���ư��ϴ�.");
						return;
					default:
						System.out.println("�߸� �Է��ϼ̽��ϴ�.\n" +
								"ó������ ���ư��ϴ�.");
						return;
				}
			}
		}
		System.out.println("������ �����ϴ�.");
	}

	private void showAll() { // ��� ���
		if (repository.isEmpty()) {
			System.out.println("�����Ͱ� �������� �ʽ��ϴ�.");
			return;
		}
		repository.forEach(System.out::println);
	}

	private boolean exit() { // ����
		System.out.print("���� �Ͻðڽ��ϱ�? ����: Y, ���: N\n" +
				">> ");
		String select = sc.nextLine().trim().toUpperCase();
		switch (select) {
			case "Y":
				System.out.println("�����մϴ�.");
				return true;
			case "N":
				System.out.println("ó������ ���ư��ϴ�.");
				return false;
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�.\n" +
						"ó������ ���ư��ϴ�.");
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
			System.out.print("�̸� : ");
			this.NAME = sc.nextLine();
			System.out.print("��ȭ��ȣ : ");
			this.PHONE_NUMBER = sc.nextLine();
		}

		@Override
		public String toString() {
			return String.format("\n" +
							"�̸� : %s\n" +
							"��ȭ��ȣ : %s",
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
			System.out.print("���� : ");
			this.MAJOR = sc.nextLine();
			try {
				System.out.print("�г� : ");
				this.YEAR = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				throw new InputMismatchException();
			}
		}

		@Override
		public String toString() {
			return super.toString() + String.format("\n" +
					"���� : %s\n" +
					"�г� : %d",
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
			System.out.print("ȸ�� : ");
			this.COMPANY = sc.nextLine();
		}
		@Override
		public String toString() {
			return super.toString() + String.format(
					"\nȸ�� : %s",COMPANY
			);
		}
	}

	private enum MenuChoice {
		PRINT_CHOICE("\n" +
				"�����ϼ���...\n" +
				"1. ������ �Է�\n" +
				"2. ������ �˻�\n" +
				"3. ������ ����\n" +
				"4. ��� ������ ����\n" +
				"5. ���α׷� ����\n" +
				"���� : "
		),
		PRINT_ADD("\n" +
				"������ �Է��� �����մϴ�\n" +
				"1. �Ϲ�, 2. ����, 3. ȸ��\n" +
				"���� >> "
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

		private static MenuChoice valueOf(int num, String option) { // enum ����ó�� 
			return Arrays.stream(MenuChoice.values())
					.filter(x -> x.NUM == num)
					.filter(x -> x.MESSAGE.equals(option))
					.findAny()
					.orElseThrow(() -> new MenuChoiceException(num));
		}

	}
}

class MenuChoiceException extends RuntimeException { // ����ó�� Ŭ����
	private final int ERR_NUM;

	MenuChoiceException(String Message, int ERR_NUM) {
		super(Message);
		this.ERR_NUM = ERR_NUM;
	}

	MenuChoiceException(int ERR_NUM) {
		this("�� �ش��ϴ� ������ �������� �ʽ��ϴ�.", ERR_NUM);
	}

	@Override
	public String getMessage() {
		return ERR_NUM + super.getMessage();
	}
}
