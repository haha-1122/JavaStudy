import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class Test {
	public static void main(String[] args) {
		run(3);
	}

	public static ArrayList<Integer> getArr(int i) {
		ArrayList<Integer> list = new ArrayList<>(i * 2);
		IntStream.generate(() -> 10)
				.limit(i)
				.forEach(list::add);
		IntStream.generate(() -> 0)
				.limit(i)
				.forEach(list::add);
		Collections.shuffle(list);
		return list;
	}

	public static int count(List<Integer> arr, int from, int to) {
		int count = 0;
		for (int i = from; i < to; i++) {
			if (arr.get(i) == 10)
				count++;
		}
		return count;
	}

	public static final int n = 10000000;
	public static final ArrayList<Integer> list = Test.getArr(n);

	public static int run(int n) {
		MyThread[] threads = new MyThread[n];
		int x = 2 * Test.n / n;
		int y = 2 * Test.n % n;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			if (i == n - 1) {
				threads[i] = new MyThread(x * i, x * (i + 1) + y);
				break;
			}
			threads[i] = new MyThread(x * i, x * (i + 1));
		}
		long start = System.currentTimeMillis();
		for (MyThread t : threads) {
			System.out.println("Threads Starting");
			t.start();

		}
		for (MyThread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {

			}
			System.out.println(t.getName() + " : joining end");
		}
		for (MyThread t : threads) {
			sum += t.getCount();
		}

		/*sum = Arrays.stream(threads)
				.parallel()
				.peek(t -> {
					t.start();
					System.out.println(t.getName() + " : started");
				})
				.peek(t -> {
					try {
						t.join();
						System.out.println(t.getName() + " : joining end");
					} catch (InterruptedException ignored) {}
				}).mapToInt(MyThread::getCount).sum();*/

		System.out.println("time : " + (System.currentTimeMillis() - start));
		return sum;
	}


}

class MyThread extends Thread {
	int x;
	int y;
	int count;

	MyThread(int x, int y) {
		System.out.println(this.getName() + " is initialized");
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " : running");
		count += Test.count(Test.list, x, y);
		System.out.println(this.getName() + " : finished");
	}

	public int getCount() {
		return count;
	}
}

