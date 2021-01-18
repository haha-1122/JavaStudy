# Stream

### 		다양한 데이터 소스를 표준화된 방법으로 다루기 위한 것

``` java
List<Integer> list = Arrays.asList(1,2,3,4,5);
Stream<Integer> intStream = list.stream(); // Collection
Stream<String> strStream = Stream.of(new String[]{"a","b","c"}); // 배열
Stream<Integer> evenStream = Stream.iterate(0, n->n+2); // 0,2,4,6, ...
Stream<Double> randomStream = Stream.generate(Math::random);
IntStream intStream = new Random().ints(5); // 난수 스트림
```



### 스트림을 다루기 위해선 크게 스트림만들기 -> 중간연산 -> 최종연산 순서로 다룬다.

- 중간 연산 - 연산결과가 스트림인 연산. 반복적으로 적용 가능 (0~ n번)
- 최종 연산 - 연산결과가 스트림이 아닌 연산. 단 한번만 적용가능(스트림의 요소를 소모) (0 ~ 1번)

``` java
stream.disinct().limit(5).sorted().forEach(Syetem.out::println);
//disinct() ~ sorted() 까지가 중간연산이고, forEach가 최종연산이다.


String[] strArr = {"dd", "aaa", "CC", "cc", "b"};
Stream<String> stream = Stream.of(strArr); // 문자열 배열이 소스인 스트림 생성
Stream<String> filteredStream = stream.filter(); 
Stream<String> distinctedStream = stream.distinct(); // 중복제거
Stream<String> sortedStream = stream.sort();
Stream<String> limitedStream = stream.limit(5); // 스트림 자르기
int total = stream.count(); // 요소 개수 세기 (반환값이 Stream이 아니라 int형인 최종연산)

```



## Stream의 특징

- **스트림은 데이터 소스로부터 데이터를 읽기만할 뿐 변경하지 않는다.**

  ``` java
  List<Integer> list = Arrays.asList(3,1,5,4,2);
  List<Integer> sortedList = list.stream().sorted() // list를 정렬해서
      					.collect(Collectors.toList()); // 새로운 List에 저장
  System.out.println(list); // 3,1,5,4,2
  System.out.println(sortedList); // 1,2,3,4,5 
  ```

- **스트림은 Iterator처럼 일회용이다. (필요하면 다시 스트림을 생성해야 한다)**

  ``` java
  strStream.forEach(System.out::println); // 모든 요소를 화면에 출력(최종연산)
  // 최종연산을 하면 스트림이 닫힌다.
  int numOfStr = strStream.count(); // 에러. 스트림이 이미 닫혔다.
  ```

- **최종 연산 전까지 중간연산이 수행되지 않는다. -지연된 연산**

  ``` java
  IntStream intStream = new Random().ints(1,46); // 1 ~ 45 범위의 무한 스트림
  intStream.distinct().limit(6).sorted() // 중간연산
      .forEach(i -> System.out.print(i + ",")); // 최종 연산
  ```

  위의 코드는 1~45 범위의 요소를 무한대로 갖고 있는 무한스트림을 열어 놓은 뒤 중복제거, 끝 자르기, 정렬을 중간 연산으로 하고, 최종 연산으로 모든 요소를 출력하는 스트림이다.

  코드만 봤을 때 무한 스트림을 열고, 순서대로 중복제거, 끝 자르기, 정렬, 프린트를 해서 결과적으로 오류가 날 것 같지만 실제론 그렇지 않다. 지연된 연산을 하기 때문인데. 스트림은 연산이 실행되기 전, 중간 연산들의 정보들만 가지고 있을 뿐 바로 순서대로 수행하지 않는다. 필요에 따라 수행하는거라 저 코드는 문제없이 동작할 수 있게 된다.

  (공부가 더 필요할듯)

- **스트림은 작업을 내부 반복으로 처리한다.**

  ```java 
  void forEach(Consumer<? super T> action) {
      Objects.requireNonNull(action); // null check
      
      for(T t : src)
          action.accept(T); // 이렇게 내부적으로 forEach문 가지고 있음. 코드를 간결하게 하기 위함.
  }
  ```

- **스트림의 작업을 병렬로 처리 - 병렬스트림** (멀티 쓰레드)

  ``` java
  Stream<String> strStream - Stream.of("dd","aaa","CC","cc","b");
  int sum = strStream.parallel() // 병렬 스트림으로 전환 (속성만 변경)
      .mapToInt(s -> s.length()).sum();
  //parallel() 메소드의 반대는 sequential이 있다.(default값임)
  ```

- **기본형 스트림 - IntStream, LongStream, DoubleStream, 기본형Stream**

  -오토박싱 & 언박싱의 비효율이 제거됨(Stream<Integer> 대신 IntStream사용)

  -숫자와 관련된 유용한 메소드를 Stream<T>보다 더 많이 제공



## 스트림 만들기

- Collection인터페이스의 stream()으로 컬렉션을 스트림으로 변환

  ``` java
  Stream<E> stream() // Collection 인터페이스의 메소드
  ```

  

- 객체 배열로부터 스트림 생성

  ``` java
  Stream<T> Stream.of(T... valuse) // 가변 인자
  Stream<T> Stream.of(T[])
  Stream<T> Arrays.stream(T[])
  Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)
  ```

  사용예시

  ``` java
  Stream<String> strStream = Stream.of("a","b","c"); // 가변 인자
  Stream<String> StrStream = Stream.of(new String[]{"a","b","c"});
  Stream<String> StrStream = Arrays.stream(new String[]{"a","b","c"});
  Stream<String> strStream = Arrays.stream(new String[]{"a","b","c"},0, 3);
  ```

- 기본형 배열로부터 스트림 생성

  Stream<T> -> Intstream 같은 형태로 바뀌고 사용법은 같다  

- 난수를 요소로 갖는 스트림

  ``` java
  IntStream intStream = new Random().ints(); // 무한 스트림
  Integer.MIN_VALUE <= inits() <= Integer.MAX_VALUE // 이런식으로 그 자료형의 최대, 최솟값 사이의 난수를 무한 스트림으로 생성
  intStream.limit(5).forEach(System.out::println);
  
  intStream insStream = new Random().ints(5); // 크기가 5인 난수 스트림을 반환.
  ```

  - 지정된 범위의 난수 (Random()클래스의 메소드)

    ``` java
    IntStream ints(int begin, int end) // 무한 스트림
    LongStream longs(long begin, long end) // ... 등등 기본값 자료형
        
    IntStream ints(long streamSize, int begin, int end) // 유한 스트트림 등등 기본값 자료형
    ```

- 특정 범위의 정수를 요소로 갖는 스트림 생성(IntStream, LongStream)

  ``` java
  IntStream IntStream.range(int begin, int end) // end 미만
  IntStraem IntStream.rangeClosed(int begin, int end) // end 이하
      
  ```

- 람다식으로 만들기 iterate(), generate()

  ``` java
  static<T> Stream<T> iterate(T seed, UnaryOperator<T> f) // 이전 요소에 종속적
  static<T> Stream<T> generate(Supplier<T> s) // 이전 요소에 독립적
  ```

  - iterate()는 이전 요소를 seed로 해서 다음 요소를 계산한다.

    ``` java
    Stream<Integer> evenStream = Stream.iterate(0, n -> n+2); // 0, 2, 4, 6
    // 0, 0+2, 2+2, 4+2, .....
    ```

  - generate()는 seed를 사용하지 않음 (이전 요소에 독립적)

    ``` java
    Stream<Double> randomStream = Stream.generate(Math::random); // 무한 랜덤~~
    Stream<Integer> oneStream = Stream.generate(()-> 1); // 1,1,1,1,1,1, ...
    ```

    따라서 generate()는 초기값(seed)가 필요하지 않다.

- 