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





## 스트림의 최종연산

-   void forEach()

-   조건 검사

    ``` java
    boolean allMatch(Predicate<? super T> predicate);
    boolean anyMatch(Predicate<? super T> predicate);
    boolean noneMatch(Predicate<? super T> predicate);
    ```

-   Optional findAny(), findFirst()

    병렬일 경우 Any, 아닌경우 First

-   통계

    ``` java
    long count();
    Optional<T> max(Comparator<? super T> comparator);
    Optional<T> min(Comparator<? super T> comparator);
    ```

-   리듀싱

    ``` java
    // 스트림의 요소를 줄여가면서 연산해 최종 결과를 반환하는 것 따라서 매개변수 타입이 BinaryOperator<T>
    // 처음 두 요소를 가지고 연산하고, 그 연산결과를 다음 요소와 연산한다.
    Optional<T> reduce(BinaryOperator<T> accumulator);
    T reduce(T identity, BinaryOperator<T> accumulator); // 초기값과 스트림의 처음 요소를 가지고 reduce시작
    // 만약 스트림의 요소가 하나도 없는 경우엔 T를 반환한다. 반환값이 Optional<T>가 아닌 이유는 애초에 초기값으로 절대 null이 나올 수 없는 구조이기 때문
    U reduce(U identity, BiFunction<U,T,U> accumulator, BinaryOperator<U> combiner); // 병렬 스트림에서 사용
    
    // count와 sum, max, min 도 reduce를 이용해 작성되었다.
    // intStream은 Stream<Integer>를 나타냄
    int count() = intStream.reduce(0, (a,b) -> a+1);
    int sum() = intStream.reduce(0, (a,b) -> a + b);
    int max() = intStream.reduce(Integer.MIN_VALUE, (a,b) -> a>b ? a:b);
    int min() = intSTream.reduce(Integer.MAX_VALUE, (a,b) -> a<b ? a:b);
    ```

-   collect()

    스트림의 요소를 수집하는 방법으로, reducing 과 유사하다. 어떻게 수집할 것인가에 대한 방법이 정의되어 있어야 하는데, 이 방법을 정의한 것이 바로 컬렉터이다.

    컬렉터는 Collector 인터페이스를 구현한 것으로, 직접 구현할 수도 있고 미리 작성된 것을 사용할 수도 있다. Collectors가 미리 작성된 다양한 종류의 컬렉터를 반환하는 static 메소드를 가지고 있다

    Collector : 컬렉터 인터페이스. collect() 메소드의 파라미터로, 이 인터페이스를 구현해야 사용 가능

    Collectors : 컬렉터 클래스. static 메소드로 미리 작성된 컬렉터 제공

    ``` java
    Object collect(Collector collector); // Collector를 구현한 클래스의 객체를 매개변수로
    Object collect(Supplier supplier, BiConsumer accumulator, BiConsumer combiner);
    ```

    아래의 메소드 처럼 매개변수가 3개있는 메소드는 잘 사용하지 않지만, Collector 인터페이스를 구현하지 않고, 간단히 람다로 수집할 때 사용하기 편하다.

    

    
### 스트림을 컬렉션과 배열로 변환

---

#### toList(), toSet(), toMap(), toCollection(), toArray()

``` java
List<String> names = stuStream.map(Student::getName)
    .collect(Collectors.toList()); // Stream<Student>를 <String>map으로 중간연산해서 그걸 collect 최종연산으로 toList()를 이용해 List<String>으로 반환
    ArrayList<String> list = names.stream()
        .collect(Collectors.toCollection(ArrayList::new)); // List나 Set이 아닌 특정 컬렉션을 지정하려면, toCollection()에 해당 컬렉션의 생성자 참조를 매개변수를 통해 넣어주면 된다.
    
    
    Map<String, Person> map = personStream
        .collect(Collectors.toMap(p -> p.getRegId(), p->p)); // Stream<Person>을 map으로 만들기 위해 key값을 Person.getRegId() 즉 주민등록번호로 하고, value를 객체 자체를 넣어서 map으로 반환
    //p -> p 대신 Function.identity(항등함수)를 사용해도 좋다.
    
    Student[] stuNames = studentStream.toArray(Student[]::new); // T[]타입의 배열로 변환하기 위해 toArray를 사용한다. 해당 타입의 생성자 참조를 매개변수로 지정해 주어야 한다. 지정해 주지 않으면 Object로 반환
    
    Student[] stuNames = studentStream.toArray(); // Error
    Object[] stuNames = studentStream.toArray(); // OK
```



### 통계

---

#### counting(), summingInt(), averagingInt(), maxBy(), minBy()

``` java
long count = stuStream.count();
long count = stuStream.collect(Collectors.counting());

long totalScore = studentStream.mapToLong(Student::getTotalScore).sum();
long totalScore = studentStream.collect(Collectors.summingLong(Student::getTotalScore));

OptionalInt topScore = studentStream.mapToInt(Student::getTotalScore).max();
Optional<Student> topStudent = studentStream
    .max(Comparator.comparingInt(Student::getTotalScore));
Optional<Student> topStudent = studentStream
    .collect(maxBy(Comparator.comparingInt(Student::getTotalScore)));

IntSummaryStatistics stat = stuSteram
    .mapToInt(Student::getTotalScore)
    .summaryStatistics(); // SummaryStatistics = 통계 요약
IntSummaryStatistics stat = stuStream
    .collect(summarizingInt(Student::getTotalScore));
```



### reducing()

---

리듀싱 역시 collect()로 가능하다. IntStream에는 매개변수가 3개짜리인 collect()만 정의되어 있으므로 boxed()를 통해 IntStream을 Stream<Integer>로 변환해야 매개변수가 1개인 collect() 메소드를 사용할 수 있다.

``` java
IntStream intStream = new Random().ints(1,46).distinct().limit(6);

OptionalInt max = intStream.reduce(Integer::max);
Optional<Integer> max = intStream.boxed().collect(Collectors.reducing(Integer::max));

long sum = intStream.reduce(0, (a,b) -> a + b);
long sum = intStream.boxed().collect(Collectors.reducing(Integer::max));
```

Collectors.reducing()엔 아래와 같이 3가지 종류가 있다. 세 번째 메소드만 제외하고 reduce()와 같다. 세 번째 것은 위의 예에서 알 수 있듯 map과 reduce()를 합쳐놓은 것이다.

``` java
Collector reducing(BinaryOperator<T> op);
Collector reducing(T identity, BinaryOperator<T> op);
Collector reducing(U identity, Function<T,U> mapper, BinaryOperator<U> op);
```

#### joining()

---

문자열 스트림의 모든 요소를 하나의 문자열로 join해서 반환한다. 구분자와 접두사, 접미사를 지정해줄 수 있다.

스트림의 요소가 String이나 StringBuffer처럼 CharSequence의 자손인 경우에만 결합이 가능하다

``` java
String studentNames = stuSteram.map(Student::getName).collector(Collectors.joining(",","{"."}"));
```

만일 map()을 사용하지 않고 객체 그대로 joining 한다면, toString()을 가지고 연산한다.





## 그룹화와 분할

그룹화는 스트림의 요소를 특정 기준으로 그룹화하는 것을 의미하고, 분할은 스트림의 요소를 두 가지, 지정된 조건에 일치하는 그룹과 일치하지 않는 그룹으로의 분할을 의미한다. groupingBy()는 스트림의 요소를 Fumction으로, partitioningBy()는 Predicate로 분류한다.

``` java
Collector groupingBy(Function classifier);
Collector groupingBy(Function classifier, Collector downstream);
Collector groupingBy(Function classifier, Supplier mapFactory, Collector downstream);

Collector partitioningBy(Predicate predicate);
Collector partitioningBy(Predicate predicate, Collector downstream);
```



groupingBy()와 partitioningBy는 분류를 Function으로 하냐 Predicate로 하냐의 차이만 있을 뿐 동일하다. 스트림을 두 개의 그룹으로 나눠야 한다면 당연히 partitioningBy()로 분할하는 것이 더 빠르다.

또한 그룹화와 분할의 결과는 Map에 담겨 반환된다.



-   #### partitioningBy()

``` java
Map<Boolean, List<Student>> stuBySex = stuStream
    .collect(Collectors.partitioningBy(Student::isMale)); // isMale을 통해 Map에 분류함.
    
   
    

Map<Boolean, Long> stuBySex = stuStream
    .collect(Collectors.partitioningBy(Student::isMale,Collectors.counting())); // Value에 Student 객체를 넣는게 아니라 counting()을 통해 남녀의 수를 나타냄
Map<Boolean, Optional<Student> stuBySex = stuStream
    .collect(Collectors.partitioningBy(Student::isMale, Collectors.maxBy(Student::compareTo)));

// Optional로 얻고싶지 않은 경우
Map<Boolean, Student> stuBySex = stuStream
    .collect(Collectors.partitioningBy(Student::isMale,
                                       Collectors.collectingAndThen(
                                       		maxBy(comparingInt(Students::getScore)),
                                            Optional::get      
                                       )
                        )
     )); // 이렇게 collectingAndThen()을 사용하면 된다.

//이중 partitioningBy
Map<Boolean, Map<Boolean, List<Student>>> failedStuBySex = stuStream
    .collect(
		Collectors.partitioningBy(Student::isMale,
                                  Collectors.partitioningBy(s -> s.getScore() < 150)
                                  )
); // 이렇게 이중분할을 사용할 수도 있다.
```





-   #### groupingBy()

    groupingBy()는 기본적으로 Collectors.toList()를 생략해 준다.

    ``` java
    Map<Integer, List<Student>> stuByBan = stuStream
        .collect(groupingBy(Student::getBan));
    
    Map<Integer, List<Student>> stuByBan = stuStream
        .collect(groupingBy(Student::getBan, toList))
    ```

    다른 Collection을 사용하고 싶다면 적절히 Map도 바꿔서 쓰면 된다. ( *toCollection()* )

    ``` java
    Map<Integer, HashSet<Student>> stuByBan = stuStream
        .collect(groupingBy(Student::getBan, Collectors.toCollection(HashSet::new)));
    ```

    좀 더 복잡하게 만들면

    ``` java
    Map<Student.Level, Long> stuByLevel = stuStream // Level은 Student의 enum이라고 생각하면 된다.
        .collect(Collectors.groupingBy(s -> {
            if(s.getScore >= 200) 			return Student.Level.HIGH;
            else if(s.getScore >= 100) 		return Student.Level.MEDIUM;
            else 							return Student.Level.LOW;
        }, 
            Collectors.counting()));
    ```

    중복해서 쓸 수도 있다.

    ``` java
    // 학년, 반 별로 저장
    Map<Integer, Map<Integer, List<Student>>> stuByHakAndBan = stuStream
        .collect(Collectors.groupingBy(Student::getHak,
                                      Collectors.groupingBy(Student::getBan)));
    // 학년, 반 별로 각 반의 1등
    Map<Integer, Map<Integer, Student>> topStuByHakAndBan = stuStream
        .collect(Collectors.groupingBy(Student::getHak,
         	Collectors.groupingBy(Student::getBan, Collectors.collectingAndThen(
            	Collectors.maxBy(Collectors.comparingInt(Student::getScore)),
            		Optional::get))));
    
    //헉년, 반별로 그룹화 한 후 성적그룹으로 변환
    Map<Integer, Map<Integer, Set<Student.Level>>> stuByHakAndBan = stuStream
        .collect(
    		Collectors.groupingBy(Student::getHak,
    			Collectors.groupingBy(Student::getBan,
    				Collectors.mapping(s-> {
                        if(s.getScore() >= 200)			return Student.Level.HIGH;
                        else if(s.getScore() >= 100)	return Student.Level.MEDIUM;
                        else							return Student.Level.LOW;
                    }, toSet())));
    ```







## Collector구현

Collector 인터페이스는 다음과 같이 정의되어 있다.

``` java
public interface Collector<T, A, R> {
    Supplier<A>				supplier();
    BiConsumer<A, T> 		accumulator();
    BinaryOperator<A>		combiner();
    Function<A, R>			finisher();
    
    Set<Characteristics>	characteristics(); // 컬렉터의 특성이 담긴 Set을 반환
    ...
}
```

직접 구현해야 하는 것은 위의 5개의 메소드이며, characteristics()를 제외하면 모두 반환타입이 함수형 인터페이스이다.(람다)

``` java
supplier();		// 작업결과를 저장할 공간을 제공
accumulator();	// 스트림의 요소를 수집할 방법을 제공
combiner();		// 두 저장공간을 병합할 방법을 제공(병렬 스트림)
finisher();		// 결과를 최종적으로 변환할 방법을 제공
```

characteristics()는 컬렉터가 수행하는 작업의 속성에 대한 정보를 제공하기 위한 것이다.

``` java
Characteristics.CONCURRENT; 		// 병렬로 처리할 수 있는 작업
Characteristics.UNORDERED;			// 스트림의 요소의 순서가 유지될 필요가 없는 작업
Characteristics.IDENTITY_FINISH		// finisher()가 Function.identity() 즉, 항등함수인 작업
```

``` java
public Set<Characteristics> characteristics() { 			// 이렇게 속성을 지정해 주면 된다.
    return Collections.unmodifiableSet(EnumSet.of(
    	Collector.Characteristics.CONCURRENT,
    	Collector.Characteristics.UNORDERED));
}

public Set<Characteristics> characteristics() {
    return Collections.emptySet();							// 지정할 특성이 없는 경우 비어있는 Set을 반환
}
```

