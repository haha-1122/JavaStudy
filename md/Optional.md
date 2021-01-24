# Optional<T>

-   **T 타입 객체의 래퍼클래스 - Optional<T>**

    ``` java
    public final class Optional<T> {
        private final T value; // T타입의 참조변수
    }
    ```

    기본 자료형들도 Wrapper 클래스가 있듯이 참조형 자료형들도 Wrapper클래스가 있다. 그게 이친구이다.

    사용하는 이유

    1.  null을 직접 다루기 위험하기 때문
    2.  null  체크를 할 때 조건문이 들어가는게 필수인데 E = mc^2 에 위반한다
    3.  NullPointerException 때문에 화가 많이나서 만들어 버린 것



## Optional 객체 생성하기

-   Optional 객체를 생성하는 방법

    ``` java
    String str = "abc";
    Optional<String> optVal = Optional.of("abc");
    Optional<String> optVal = Optional.of(null); // NullPointerException 발생
    Optional<String> optVal = Optional.ofNullable(null); // OK
    ```





-   null대신 빈 Optional<T> 객체를 사용하는게 좋다.

    ``` java
    Optional<String> optVal = null; // 바람직하지 않음.
    Optional<String> optVal = Optional.<String>empty(); // 빈 객체로 초기화
    ```

    





## Optional 객체의 값 가져오기

-   get(), orElse(), orElseGet(), orElseThrow()

    ``` java
    Optional<String> optVal = Optional.of("abc");
    String str = optVal.get(); // optVal에 저장된 값을 반환. null이면 예외발생
    String str2 = optVal.orElse(""); // optVal에 저장된 값이 null일 때는, ""반환
    String str3 = optVal.orElseGet(String::new); // 람다식 사용가능 (Supplier)
    String str4 = optVal.orElseThrow(NullPointerException::new); // null이면 예외발생
    ```





-   isPresent() - Optional객체의 값이 null이면 false, 아니면 true

    ``` java
    if(Optional.ofNullable(str).isPresent()) {// if(str!= null)
       System.out.println() }
    
    ifPresent(Consumer) // null이 아닐때만 작업 수행, null이면 아무 일도 안함
    Optional.ofNullable(str).ifPresent(System.out::println);
    ```



### 람다나 스트림이나 optional은 코드의 가독성을 높여주지만 성능을 약간 희생하는 단점이있음 자주 사용하는 친구들을 메소드 안에 숨겨놓기 때문





## OptionalInt, OptionalLong, OptionalDouble

-   기본형 값을 감싸는 래퍼클래스

    ``` java
    public final class OptionalInt {
        //...
        private final boolean isPresent; // 값이 저장돼있으면 true
        private final int value; // int 타입의 변수 Optional<T>와 다르게 얜 기본형 자료형을 저장함
        // 따라서 오토박싱을 하는 다른 Optional보다 성능을 더 챙길 수 있음.
    }
    ```





-   OptionalInt의 값 가져오기 - int getAsInt() Long이나 Double도 비슷함





-   Optional객체와 비교

    ``` java
    OptionalInt opt = OptionalInt.of(0); // 0을 저장
    OptionalInt opt2 = OptionalInt.empty(); // 0을 저장 근데 isPresent가 false
    opt.equals(opt2) = false; // 하나는 null이라서그럼
    ```

    