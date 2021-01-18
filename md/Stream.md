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

- 파일을 소스로 하는 스트림 생성하기

  ``` java
  Stream<Path> Files.list(Path dir) // Path는 파일 또는 디렉토리
  ```

  ``` java
  Stream<String> Files.lines(Path path) // 파일 내용을 라인 단위로 읽어서 String으로 만듦
  Stream<String> Files.lines(Path path, Charset cs)
  Stream<String> lines() // BufferedReader클래스의 메소드
  ```

- 비어있는 스트림 생성하기

  ``` java
  Stream emptyStream = Stream.empty(); // empty()는 빈 스트림을 생성해서 반환함.
  long count = emptyStream.count(); // count의 값은 0이 나옴
  ```




## 스트림의 연산 메소드들

-   중간연산 (0 ~ n번 수행 가능)

    ```java
    Stream<T> distinct() // 중복을 제거
    Stream<T> filter(Predicate<T> predicate) // 필터링
    Stream<T> limit(long maxSize) // 스트림의 일부를 잘라냄
    Stream<T> skip(long n) // 스트림의 일부를 건너뜀
    Stream<T> peek(Consumer<T> action) // 스트림의 요소에 작업수행
    Stream<T> sorted()
    Stream<T> sorted(Comparator<T> comparator) // 스트림의 요소를 정렬
        
      	// 스트림의 요소를 변환
    Stream<R> map(Function<T, R> mapper)
    DoubleStream mapToDouble(ToDoubleFunction<T> mapper)
    IntStream mapToInt(ToIntFunction<T> mapper)
    LongStream mapToLong(ToLongFUnction<T> mapper)
    
    Stream<R> flatMap(Function<T, R> mapper)
    DoubleStream flatMapToDouble(ToDoubleFunction<T> mapper)
    IntStream flatMapToInt(ToIntFunction<T> mapper)
    LongStream flatMapToLong(ToLongFUnction<T> mapper)
    ```

    

-   최종연산 (0 ~ 1번 수행 가능)

    ``` java
    void forEach(Consumer<? super T> action) // 각 요소에 지정된 작업 수행
    void forEachOrdered(Consumer<? super T> action) // 순서유지. 병렬스트림처리할 때 사용
        
    long count() // 스트림의 요소의 개수 반환
    
    Optional<T> max(Comparator<? super T> comparator) // 스트림의 최대값/최소값을 반환
    Optional<T> min(Comparator<? super T> comparator)
        
    Optional<T> findAny() // 아무거나 하나를 반환
    Optional<T> findFirst() // 첫 번째 요소를 반환
        
    boolean allMatch(Predicate<T> p) // 모두 만족하는지 확인
    boolean anyMatch(Predicate<T> p) // 하나라도 만족하는지
    boolean noneMatch(Predicate<T> p) // 모두 만족하지 않았는지
        
    Object[] toArray()
    A[] toArray(IntFunction<A[]> generator) // 스트림의 모든 요소를 배열로 반환
        
        // 스트림의 요소를 하나씩 줄여가면서(리듀싱) 계산한다
    Optional<T> reduce(BinaryOperator<T> accumulator)
    T reduce(T identity, BinaryOperator<T> accumulator)
    U reduce(U identity, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner)
        
        // 스트림의 요소를 수집한다. 주로 요소를 그룹화하거나 분할한 결과를 컬렉션에 담아 반환하는데 사용된다.
    R collect(Collector<T, A, R> collector)
    R collect(Supplier<R> supplier, BiConsumer<R, T> accumulator, BiConsumer<R,R> combiner) 
    ```

    

## 스트림의 중간연산

- 스트림 자르기 -skip(), limit()

  ``` java
  Stream<T> skip(ling n) // 앞에서부터 n개 건너뛰기
  Stream<T> limit(long maxSize) // maxSize 이후의 요소는 잘라냄
  
  ```





- 스트림의 요소 걸러내기 -filter(), distinct()

  ``` java
  Stream<T> filter(Predicate<? super T> predicate) // 조건에 맞지 않는 요소 제거
  Stream<T> distinct() // 중복제거
  ```





- 스트림 정렬하기 -sorted()

  ``` java
  Stream<T> sorted() // 스트림 요소의 기본 정렬(Comparable)로 정렬
  Stream<T> sorted(Comparator<? super T> comparator) // 지정된 Comparator로 정렬
  ```

  **ex) 문자열의 경우**

  ``` java
  strStream.sorted(); // 기본정렬
  strStream.sorted(Comparator.natualOrder()); // 기본 정렬
  strStream.sorted((s1, s2) -> s1.compareTo(s2));
  strStream.sorted(String::compareTo);
  strStream.sorted(Comparator.comparing(String::length)) // 길이 순 정렬
  // 이 밖에 재밌는 기능 많음
  ```






- Comparator의 comparing()메소드로 정렬 기준을 제공

    ``` java
    Comparator<T> comparing(Function<T, U> keyExtractor)
    Comparator<T> comparing(Function<T, U> keyExtractor, Comparator<U> keyComparator)
    ```

    ``` java
    studentStream.sorted(Comparator.comparing(Student::getBan)) // 반별로 정렬
        .forEach(System.out::println);
    ```

    ``` java
    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
                Function<? super T, ? extends U> keyExtractor)
        {
            Objects.requireNonNull(keyExtractor);
            return (Comparator<T> & Serializable)
                (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
        } // 이렇게생겨먹었는데
    /* 파라미터로 Function 인터페이스 만족시키는 람다 들어오면 그냥 그거갖고*/
    (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
    /*다시 이 람다식을 반환 때려서*/
    new Comparator<T>(){
    	@Override
        public int compare(T t1, T t2) {
            return keyExtractor.apply(t1).compareTo(keyExtractor.apply(t2));
        }
    } // 이런식으로 해주는듯 하다.
    ```

    1.  Function 인터페이스 람다 파라미터로 받음

    2.  return (c1, c2) -> f.apply(c1).compareTo(f.apply(c2))

    3.  new Comparator<>(){@Override}

    4.  sorted(new Comparator<>(){@Override})

    5.  아래건 위에꺼 좀 더 풀어쓴 느낌임

    6.  ``` java
        comparing(Student::getBan, (x,y) -> x.compareTo(y))
        ```





- 추가 정렬 기준을 제공할 떄는 thenComparing()을 사용

    ``` java
    thenComparing(Comparator<T> other)
    thenComparing(Function<T, U> keyExtractor)
    thenComparing(Function<T, U> keyExtractor, Comparator<U> keyComp)
    ```

    ``` java
    studentStream.sorted(Comparator.comparing(Student::getBan) //반별로 정렬
                        .thenComparing(Student::getTotalScore) // 총점별로 정렬
                        .thenComparing(Student:getName)) // 이름별로 정렬
        				.forEach(System.out::println);
    ```





-   스트림의 요소 변환 - map()

    ``` java
    Stream<R> map(Function<? super T, ? extends R> mapper) // Stream<T> -> Stream<R>
    ```

    ``` java
    Stream<File> fileStream = Stream.of(new File("EX1.java"),
                                        new File("Ex1"),
                                        new File("Ex1.bak"),
                                        new File("Ex1.txt"));
    Stream<String> filenameStream = fileStream.map(File::getName);
    filenameStream.forEach(System.out::println); // 스트림의 모든 파일 이름 출력
    ```

    map을 이용해 기존의 스트림의 요소를 변환해 새로운 스트림으로 반환해 준다. mapping의 그 맵이다.





-   스트림의 요소를 소비하지 않고 엿보기 - peek()

    ``` java
    Stream<T> peek(Consumer<? super T> action) // 중간 연산(스트림을 소비x)
    void forEach(Consumer<? super T> action) // 최종 연산(스트림을 소비o)
    ```

    peek(System.out::println)이 Stream으로 반환하기 때문에 최종연산이 아님 따라서 계속 Stream을 사용할 수 있다





-   스트림의 스트림을 스트림으로 변환 - flatMap()

    ``` java
    Stream<String[]> strArrStrm = Stream.of(new String[]{"abc","def","ghi"},
                                            new String[]{"ABC","DEF","GHI"});
    ```

    ``` java
    Stream<Stream<String>> strStrStrm = strArrStrm.map(Arrays::stream);
    ```

    위와 같이 한다면

    1.  strArrStrm에 두개의 String[]배열을 가진 Stream의 주소값이 저장됨.

    2.  strStrStrm에 두개의 Stream<String>을 가진 스트림의 스트림의 주소값이 저장됨.

        (위는 String[]을 가진 Stream, 아래는 Stream<String>을 가진 Stream)

    3.  따라서 저렇게 하면 두개의 배열을 하나로 합친것 처럼 사용할 수 없음.

    ``` java
    Stream<String> strStrStrm = strArrStrm.flatMap(Arrays:stream); // Arrays.stream(T[])
    ```

    이렇게 해주면 strStrStrm에 마치 new String[]{"abc","def","ghi","ABC","DEF","GHI"} 넣은것처럼 된다.

