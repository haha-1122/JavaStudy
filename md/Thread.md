

# 프로세스와 쓰레드



- 프로그램이란 실행 가능한 파일을 말하고, 프로세스는 실행중인 프로그램을 말한다. 즉 어떠한 파일이 실행중이라는 것은 프로세스가 운영체제의 메모리에 상주하고 있다는것을 뜻한다

- 프로세스는 실행중인 프로그램으로, 자원(resource)과 쓰레드로 구성 되어있다.

- 쓰레드는 프로세스 내에서 실제 작업을 수행하는 역할을 한다. 따라서 모든 프로스세는 하나 이상의 쓰레드를 가진다.

  > 프로세스 : 쓰레드 = 공장 : 일꾼 // 이렇게 비교할 수 있다.

- 싱글 쓰레드 프로세스 : 자원 + 쓰레드 , 멀티 쓰레드 프로세스 : 자원 + 쓰레드 + 쓰레드 + ...

- 

### 멀티프로세스 vs 멀티쓰레드

```
하나의 새로운 프로세스를 생성하는 것보다
하나의 새로운 쓰레드를 생성하는 것이 더 적은 비용이 든다.
```



### 멀티쓰레드의 장단점

```
많은 프로그램들이 멀티쓰레드로 작성되어 있다.
그러나, 멀티쓰레드 프로그래밍이 장점만 있는 것은 아니다.
```



| 장점 | - 자원을 보다 효율적으로 사용할 수 있다.<br />- 사용자에 대한 응답성(Responseness)이 향상된다.<br />- 작업이 분리되어 코드가 간결해 진다.<br />- 기타 등등 |
| :--: | :----------------------------------------------------------: |
| 단점 | - 동기화(Synchronization)에 주의해야 한다.<br />- 교착상태(dead-lock)가 발생하지 않도록 주의해야 한다.<br />- 각 쓰레드가 효율적으로 고르게 실행될 수 있게 해야 한다<br /> - 프로그래밍할 때 고려해야 할 사항들이 많다. |



##### 교착상태(Dead-Lock) : 다수의 쓰레드가 lock을 획득하기 위해 기다리는데, 이 lock을 잡고 있는 쓰레드도 똑같이 다른 lock을 기다리며 서로 블록 상태에 놓이는 것을 말한다. 데드락은 다수의 쓰레드가 같은 lock을 동시에, 다른 명령에 의해, 획득하려 할 때 발생할 수 있다.

예를 들자면, thread1 이 A의 lock을 가지고 있는 상태에서 B 의 lock을 획득하려 한다. 마찬가지로, thread2 는 B의 lock을 가진 상태에서 A의 lock을 획득하려 한다. 이러면 데드락이 생긴다. A 와 B는 서로 절대 원하는 lock을 얻을 수 없고, 두 쓰레드 중 어느쪽도 이 사실을 모른다

``` 
Thread 1 locks A, waits for B
Thread 2 locks B, waits for A
```

코드로 표현하면 이렇다

``` java
public class TreeNode {
    TreeNode parent = null;
    List children = new ArrayList();
    
    public synchronized void addChild(TreeNode child) {
        if(!this.children.contains(child)) {
            this.children.add(child);
            child.setParentOnly(this);
        }
    }
    
    public synchronized void addChildOnly(TreeNode child) {
        if(!this.children.contains(child)) {
            this.children.add(child);
        }
    }
    
    public synchronized void setParent(TreeNode parent) {
        this.parent = parent;
        parent.addChildOnly(this);
    }
    
    public synchronized void setParentOnly(TreeNode parent) {
        this.parent = parent;
    }
}
```



Thread 1이 parent.addChild(child) 메소드를, Thread 2 는 child.setParent(parent) 메소드를 각각 동시에, 같은 parent와 child 인스턴스에 호출한다면, 데드락이 발생할 수 있다.

> 나중에 데드락에 대해 다시 공부해야함



### 쓰레드의 구현과 실행

``` java
class MyThread extends Thread { //Thread 클래스를 상속
    @Override
    public void run() { /* 작업내용 */} //Thread 클래스의 run()을 오버라이딩.
}

class MyThread implements Runnable {
    public void run() {/* 작업내용 */} // Runnable인터페이스의 추상메서드 run()을 구현
}

Thread thread = new Thread(new Runnable(){ //생성자의 파라미터로 Runnable interface
    @override
    public void run() {/* 작업내용 */} 
});
```



### start() & run()

``` java
class ThreadTest {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
    }
}
class MyThread extends Thread {
    public void run() {}
}
```



![Thread01](C:\Users\현서\OneDrive\git\javaStudy\md\img\Thread01.png)

main 메소드에서 쓰레드를 실행했을 때 의 과정이다.

콜 스택에서 메인 메소드를 통해 start() 메소드가 실행되면 start() 메소드는 새로운 콜 스택을 만든다.

그리고 그 콜스택에 run() 메소드가 담긴 후 start() 메소드는 종료된다.

이래서 main 메소드가 종료되더라도, run 메소드가 계속 유지되어 프로그램이 종료되지 않을 수 도 있다.



### 싱글쓰레드 vs 멀티쓰레드

``` java
class ThreadTest {
    public static void main(String[] args) {
        for(int i = 0; i<300; i++) {
            System.out.println("-")
        }
        
        for(int i = 0; i<300; i++) {
            System.out.println("|");
        }
    }
}

class ThreadTest2 {
    public static void main(String[] args) {
        MyThread1 th1 = new MyThread1();
        MyThread2 th2 = new MyThread2();
        th1.start();
        th2.start();
    }
}
class MyThread1 extends Thread {
    public void run() {
        for(int i=0; i<300; i++) {
            System.out.println("-");
        }
    }
}
class MyThread2 extends Thread {
    public void run() {
        for(int i=0; i<300; i++) {
            System.out.println("|");
        }
    } 
}
```

자, 이렇게 구현하고 돌렸다고 가정하자.

싱글쓰레드일 경우에 작업자가 한명이니까 다수의 일을 동시에 수행할 수 없다. 따라서 -가 300개 출력되고, |가 300개 출력될 것 이다. 이 순서는 절대로 바뀌지 않는다.

하지만 멀티 쓰레드일 경우엔 작업자가 다수이다. 지금 이 상황에선 작업자가 두명이고, 작업도 두개다.  그리고 그 작업은 번갈아가며 동시에 수행된다. 따라서 -와 | 가 엇갈리며 출력될 것이다. 300개씩은 출력하는데 어떠한 규칙으로 출력되는지는 알 수 없다. 작업은 <b>os의 스케쥴러</b>가 관리하기 때문이다.

이렇게 구현한다면 어떤게 좋은 프로그래밍이라고 할 수 있을까

시간적인 측면에서 본다면, 멀티쓰레드가 작업을 동시에 수행하니, 멀티쓰레드쪽이 더 효율적이라고 판단할 수 있지만, 답은 싱글쓰레드가 약간 더 빠르다. 작업을 하다 다른 작업으로 넘어갈 때 **Context Switching**을 하게되는데, 이 때 시간이 소요된다. 

하지만 효율 면에서 훨씬 좋다. 싱글 쓰레드라면, 채팅을 예로 들어 파일을 업로드해주는 동안 채팅을 입력할 수 없다.



### Thread I/O Blocking

​	Thread I/O Blocking이란, 어떤 쓰레드에서 입출력할 때 작업이 중단되는 것을 말한다.  우리가 자바에서 Scanner로 신나게 입력을 할 때에도 입력이 들어가기 전에 프로그램이 자동으로 실행되지 않듯이 입력을 받을 때 계속 기다리는 것을 말한다. 이럴 때 멀티 쓰레드로 구현한다면, A에서 I/O Blocking이 일어나더라도, B에선 다른 작업을 계속 할 수 있을 것이다.

### Thread Priority of Thread

​	작업의 중요도에 따라 쓰레드의 우선순위를 다르게 하여 특정 쓰레드가 더 많은 작업시간을 갖게 할 수 있다.

``` java
void setPriority(int newPriority) // 쓰레드의 우선순위를 지정한 값으로 변경.
int getPriority() // 쓰레드의 우선순위를 반환.
    
public static final int MAX_PRIORITY = 10; // 최대우선순위
public static final int MIN_PRIORITY = 1; // 최소우선순위
public static final int NORM_PRIORITY = 5; // 보통우선순위

//우선순위가 높은 쓰레드가 더 많은 작업 시간을 가질 수 있다.
```

​	JVM은 1~10까지의 우선순위를 가지고 있고, window는 32단계를 가지고 있다.

​	또한, JVM에서의 쓰레드 우선순위는 단지 희망사항일 뿐이다. 쓰레드는 결국 OS스케쥴러가 관리하가 때문이다.



### Thread Group

- 서로 관련된 쓰레드를 그룹으로 묶어서 다루기 위한 것
- 모든 쓰레드는 반드시 하나의 쓰레드 그룹에 포함되어 있어야 한다.
- 쓰레드 그룹을 지정하지 않고 생성한 쓰레드는 main쓰레드 그룹에 속한다.
- 자신을 생성한 쓰레드(부모 쓰레드)의 그룹과 우선순위를 상속받는다.

``` java
Thread(ThreadGroup group, String name);
Thread(ThreadGroup group, Runnable target);
Thread(ThreadGroup group, Runnable target, String name);
Thread(ThreadGroup group, Runnable target, String name, long stackSize);

ThreadGroup getThreadGroup(); // 쓰레드 자신이 속한 쓰레드 그룹을 반환한다.
void uncaughtException(Thread t, Throwable e); //처리되지 않은 예외에 의해 쓰레드 그룹의 쓰레드가 실행이 종료되었을 때, JVM에 의해 이 메소드가 자동으로 호출된다. JVM은 기본적으로 printStackTrace() 를 호출하는데, 이걸 오버라이딩 해 줄 수 있다.
```



![Thread02](C:\Users\현서\OneDrive\git\javaStudy\md\img\Thread02.png)