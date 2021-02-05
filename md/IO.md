# IO



## 입출력(I/O)과 스트림(Stream)

입출력이란 입력과 출력을 줄여 부르는 말로 두 대상 간의 데이터를 주고 받는 것을 말한다.

스트림이란 데이터를 운반하는데 사용되는 연결 통로를 일컫는다. 연속적인 데이터의 흐름을 물(Stream)에 비유해서 붙여진 이름으로 하나의 스트림으로 입출력을 동시에 수행할 수 없다(단방향 통신) 따라서 입출력을 동시에 수행하려면, 2개의 스트림이 필요하다.

스트림은 먼저 보낸 데이터를 먼저 받게 되어있으며 중간에 건너뜀 없이 연속으로 데이터를 주고 받는다. 큐와 같은 FIFO구조로 되어있다.



### 바이트 기반 스트림

---



***데이터를 byte단위로 주고 받는 스트림***

-   종류

    |      입력스트림      | 출력스트림            | 입출력 대상의 종류 |
    | :------------------: | --------------------- | ------------------ |
    |   FileInputStream    | FileOutputStream      | 파일               |
    | ByteArrayInputStream | ByteArrayOutputStream | 메모리             |
    |   PipedInputStream   | PipedOutputStream     | 프로세스           |
    |   AudioInputStream   | AudioOutputStream     | 오디오 장치        |

이들은 모두 입출력 스트림의 최고 조상인 InputStream, OutputStream의 자손들이며, 각각 읽고 쓰는데 필요한 추상메서드를 알맞게 구현해놓았다.



### InputStream, OutputStream

---

이 둘은 바이트 기반 입력 스트림의 최고 조상이다.

대표적으로 다음과 같은 메소드를 가지고 있다.

-   InputStream

``` java
int available() // 스트림으로부터 읽어 올 수 있는 데이터의 크기 반환
void close() // 스트림을 닫음으로써 사용하고 있던 자원을 반환
void mark(int readlimit) // 현재 위치를 표시해놓는다. 후에 reset()에 의해서 표시해 놓은 위치로 다시 돌아갈 수 있다. readlimit은 되돌아갈 수 있는 byte의 수이다.
boolean markSupported() // mark()와 reset()을 지원하는지를 알려준다. mark()와 reset()기능을 지원하는 것은 선택적이기 때문
abstract int read() // byte룰 읽어온다(0~255 사이의 값).
int read(byte[] b) // 배열 b의 크기만큼 읽어서 배열을 채우고 읽어온 데이터의 수를 반환한다. 당연하지만, 반환크기는 배열 크기 이하이다.
int read(byte[] b, int off, int len) // 최대 len개의 byte를 읽어서, 배열 b의 지정된 위치(off)부터 저장한다.
void reset() // 스트림에서의 위치를 마지막으로 mark()가 호출되었던 위치로 되돌린다.
long skip(long n) // 스트림에서 주어진 길이(n)만큼을 건너뛴다.
```

-   OutputStream

``` java
void close() // 입력소스를 닫음으로써 사용하고 있던 자원을 반환한다.
void flush() // 스트림의 버퍼에 있는 모든 내용을 출력소스에 쓴다.
abstract void write(int b) // 주어진 값을 출력소스에 쓴다.
void write(byte[] b) // 주어진 배열 b에 저장된 모든 내용을 출력소스에 쓴다.
void write(byte[] b, int off, int len) // 주어진 배열 b에 저장된 내용 중에서 off번째부터 len개 만큼만 읽어서 출력소스에 쓴다.
```

***OutputStream의 filter() 메소드는 버퍼가 있는 출력스트림의 경우에만 의미가 있다. OutputStream에 정의된 flush()는 아무런 기능을 가지고 있지 않다***

***프로그램이 종료될 때 JVM이 자동으로 스트림을 닫아주기는 하지만, 스트림을 사용하는 작업은 반드시 close()를 통해 닫아주어야 한다. 그러나 ByteArrayInputStream과 같이 메모리를 사용하는 스트림과 System.in, System.out과 같은 표준 입출력 스트림은 닫지 않아도 된다***

### 보조 스트림

---

보조스트림은 스트림의 기능을 보완하기 위한 스트림이다. 실제 데이터를 주고받는 스트림이 아니기 때문에 데이터를 입출력할 수 있는 기능은 없지만, 스트림의 기능을 향상시키거나 새로운 기능을 추가할 수 있다. 

모든 보조스트림은 FilterInputStream, FilterOutputStream의 자손이고, 이들 또한 InputStream, OutputStream 의 자손이기때문에 입출력 방법이 같다.

|         입력          |         출력         |                       설명                       |
| :-------------------: | :------------------: | :----------------------------------------------: |
|   FilterInputStream   |  FilterOutputStream  |            필터를 이용한 입출력 처리             |
|  BufferedInputStream  | BufferedOutputStream |             버퍼를 이용한 성능 향상              |
|    DataInputStream    |   DataOutputStream   |    기본 자료형 단위로 데이터를 처리하는 기능     |
|  SequenceInputStream  |         없음         |           두 개의 스트림을 하나로 연결           |
| LineNumberInputStream |         없음         |       읽어 온 데이터의 라인 번호를 카운트        |
|   ObjectInputStream   |  ObjectOutputStream  |       데이터를 객체단위로 읽고 쓰는데 사용       |
|         없음          |     PrintStream      |     버퍼를 이용하며 추가적인 print관련 기능      |
|  PushbackInputStream  |         없음         | 버퍼를 이용해 읽어온 데이터를 다시 되돌리는 기능 |



### 문자기반 스트림

---

자바에선 C언어와 달리 한 문자를 의미하는 char형이 1byte가 아닌 2byte이기 때문에 바이트기반의 스트림으로 2byte인 문자를 처리하는데 어려움이 있다. 이 점을 보완하기 위해 제공되는 스트림이 문자기반 스트림이다.

|             바이트기반 보조스트림             |        문자기반 보조스트림         |
| :-------------------------------------------: | :--------------------------------: |
| BufferedInputStream<br />BufferedOutputStream | BufferedReader<br />BufferedWriter |
|   FilterInputStream<br />FilterOutputStream   |   FilterReader<br />FilterWriter   |
|       LineNumberInputStream(deprecated)       |          LineNumberReader          |
|                  PrintStream                  |            PrintWriter             |
|              PushbackInputStream              |           PushbackReader           |





### 바이트기반 보조스트림



#### ByteArrayInputStream & ByteArrayOutputStream

---

이 친구들은 메모리, 즉 바이트배열에 데이터를 입출력 하는데 사용되는 스트림으로, 주로 다른 곳에 입출력하기 전에 데이터를 임시로 바이트배열에 담아서 변환 등의 작업을 하는데 사용한다.

바이트 배열은 사용하는 자원이 메모리밖에 없으므로 GC에 의해 자동으로 자원을 반환하기 때문에 스트림을 닫지 않아도 된다.

바이트 배열을 사용할 때 하나의 바이트배열에 값을 저장하고 그 값을 출력하는 방식으로 쓸텐데, 이 과정에서 새로운 값을 입력할 때 성능을 위해 바이트배열에 저장된 값을 지우고 쓰는 것이 아니라, 그냥 기존의 내용 위에 값을 덮어쓴다. 따라서 read() 메소드를 이용해 바이트배열을 잘 읽어와야 한다.



#### FilterInputStream & FilterOutputStream

---

이 친구들은 InputStream, OutputStream의 자손이면서, 모든 보조 스트림의 조상이다.

이 친구들의 모든 메소드는 단순히 기반 스트림의 메소드를 그대로 호출할 뿐이며, 이는 보조 스트림 자체로는 아무런 일도 하지 않음을 의미한다.



#### BufferedInputStream & BufferedOutputStream

---

스트림의 입출력 효율을 높이기 위해 버퍼(*바이트배열* )를 사용하는 보조스트림이다.

버퍼의 크기는 입력소스로부터 한 번에 가져올 수 있는 데이터의 크기로 지정하면 좋으며, 보통 입력소스가 파일인 경우 8K 정도의 크기로 하는 것이 보통이다.

외부의 입력소스로부터 읽는 것보다 내부의 버퍼로 부터 읽는 것이 훨씬 빠르기 때문에 그만큼 작업 효율이 높아진다.

생성자를 통해 버퍼의 사이즈를 지정해줄 수 있으며 출력의 경우 버퍼가 가득 차면 버퍼의 모든 내용을 출력소스에 출력한다. 따라서 마지막 출력부분이 출력되지 않을 수 있기 때문에 flush() 메소드를 통해 남은 버퍼를 출력시켜주어야 한다. *close()메소드는 flush()메소드를 따로 호출해준다*



#### DataInputStream & DataOutputStream

---

이 친구들은 데이터를 읽고 쓰는데 있어서 byte단위가 아닌, 8가지 기본 자료형의 단위로 읽고 쓸 수 있다.

DataOutputStream이 출력하는 형식은 각 기본 자료형 값을 16진수로 표현하여 저장한다.

각 자료형의 크기가 다르므로, 출력한 데이터를 다시 읽어 올 때는 출력했을 때의 순서를 염두에 두어야 한다.

DataInputStream의 메소드의 경우 더이상 읽을 값이 없으면 EOFException을 발생시킨다.

DataOutputStream은 이진 데이터(Binary Data)로 저장되며, 문자 데이터(Text Data)가 아니므로 문서 편집기로 파일을 열어봐도 글자가 깨져 알 수 없다. 따라서 파일을 16진수 코드로 볼 수 있는 프로그램이나, ByteArrayOutputStream을 사용하여 이진데이터를 확인해야 한다.



#### SequenceInputStream

---

여러 개의 입력스트림을 연속적으로 연결해서 하나의 스트림으로부터 데이터를 읽는 것과 같이 처리할 수 있도록 도와준다.



#### PrintStream

---

데이터를 기반 스트림에 다양한 형태로 출력할 수 있는 print, println, printf와 같은 메소드를 오버로딩하여 제공한다.

PrintStream은 데이터를 적절한 문자로 출력하는 것이기 때문에 문자기반 스트림의 역할을 수행한다. JDK1.1에서 보다 향상된 기능인 PrintWriter가 추가되었으나 그 동안 매우 빈번하게 사용되던 System.out이 PrintStream이다 보니 둘 다 사용할 수 밖에 없게 되었다.

따라서 가능하면 PrintWriter를 쓰는 것이 좋다.

print()나 println()을 이용해서 출력하는 중에 PrintStream의 기반스트림에서 IOException이 발생하면 checkError()를 통해서 인지할 수 있다. print() 같은 메소드는 예외를 던지지 않고 내부에서 처리되도록 정의되었는데, 이는 너무 빈번하게 사용되는 메소드이기 때문이다.



### 문자기반 스트림



#### Reader & Writer

---

바이트 기반 스트림의 조상이 InputStream, OutputStream인것 처럼 이들도 문자기반 스트림의 조상이다.

바이트 기반 스트림과 다르게, byte배열 대신 char배열을 사용한다.

문자기반 스트림은 2byte로 데이터를 처리하며, 여러 종류의 인코딩과 자바에서 사용하는 유니코드간의 변환을 자동적으로 처리해준다. Reader는 특정 인코딩을 읽어 유니코드로 변환하고, Writer는 유니코드를 특정 인코딩으로 변환하여 저장한다.



#### PipedReader & PipedWriter

---

쓰레드 간에 데이터를 주고받을 때 사용한다. 다른 스트림과 달리 입력과 출력스트림을 하나의 스트림으로 연결하여 데이터를 주고받는다는 특징이 있다.

스트림을 생성한 다음 한쪽 쓰레드에서 connect()를 호출해 입력스트림과 출력 스트림을 연결한다. 입출력을 마친 후에는 어느 한쪽 스트림만 닫아도 나머지 스트림은 자동으로 닫힌다



#### StringReader & StringWriter

---

CharArrayReader, CharArrayWriter와 같이 입출력 대상이 메모리인 스트림이다. StringWriter에 출력되는 데이터는 내부의 StringBuffer에 저장되며 StringWriter의 다음과 같은 메소드를 이용하여 저장된 데이터를 얻을 수 있다.

``` java
StringBuffer getBuffer(); // StringWriter에 출력한 데이터가 저장된 StringBuffer를 반환
String toString(); // StringWriter에 출력된 문자열을 반환
```





### 문자기반 보조스트림



#### BufferedReader & BufferedWriter

---

버퍼를 이용하여 입출력의 효율을 높일 수 있도록 해주는 역할을 한다. 버퍼를 이용하면 입출력의 효율이 비교할 수 없을 정도로 좋아지기 때문에 사용하는 것이 좋다.



#### InputStreamReader & OutputStreamWriter

---

바이트기반 스트림을 문자기반 스트림으로 연결시켜주는 역할을 한다. 그리고 바이트기반 스트림의 데이터를 지정된 인코딩의 문자데이터로 반환하는 작업을 수행한다.

생성자를 통해 인코딩을 지정해줄 수 있으며, 생략할 경우 OS에서 사용하는 기본 인코딩을 사용한다.

getEncoding() 메소드를 통해 현재 인코딩 정보를 알 수 있다.



## 표준입출력과 File



#### 표준입출력 - System.in, System.out, System.err

---

표준입출력은 콘솔을 통한 데이터 입력과 콘솔로의 데이터 출력을 의미한다. 자바에서는 표준 입출력을 위해 3가지 입출력 스트림을 제공하는데*(in, out, err)* 이 들은 자바 어플리케이션의 실행과 동시에 생성되기 때문에 개발자가 별도로 스트림을 생성하여 지정해 줄 필요가 없다.

이들의 타입은 InputStream과 PrintStream이지만 실제로는 BufferedInputStream, BufferedOutputStream의 인스턴스를 사용한다.

콘솔 입력은 버퍼를 가지고 있기 때문에 Backspace키를 이용하여 편집이 가능하며, 한번에 버퍼의 크기만큼 입력이 가능하다. 그래서 Enter키나 Ctrl+z를 누르기 전 까지는 Blocking statement에 머무르게 된다

콘솔에서 Enter키를 누르면 \r\n이 수행되며, \r*(carriage return)* 즉 커서를 현재 라인의 첫 번째 컬럼으로 이동시키고 \n을 통해 개행한다.



#### 표준입출력의 대상변경 - setOut(), setErr(), setIn()

---

초기에는 in,out,err의 입출력 대상이 콘솔이지만 이 메소드들을 통해 다른 입출력 대상으로 변경하는 것이 가능하다.



#### RandonAccessFile

---

자바는 입력과 출력이 각각 분리되어 별도로 작업을 하도록 설계되어 있는데, 이 친구는 하나의 클래스로 파일에 대한 입출력을 모두 처리할 수 있다.

InputStream이나 OutputStream을 상속받지 않고, DataInput 인터페이스와 DataOutput인터페이스를 모두 구현하여 입출력이 모두 가능하다.

DataInputStream이나 DataOutputStream 또한 DataInput, DataOutput 인터페이스를 구현했기 때문에, 이 친구도 기본 자료형 단위로 데이터를 읽고 쓸 수 있다.

RandomAccessFile클래스의 가장 큰 장점은 다른 입출력 클래스와 다르게 반드시 파일의 첫번째 위치에서 읽어야 하는게 아닌, 읽고 쓰는 위치에 제약이 없다는 것 이다.

 이것을 가능하게 하기 위해 내부적으로 파일 포인터를 사용하는데, 입출력 시에 작업이 수행되는 곳이 바로 파일 포인터가 위치한 곳이 된다.
현재 작업 중인 파일에서 파일 포인터의 위치를 알고 싶을 때는 getFilePointer()를 사용하면 되고, 파일 포인터의 위치를 옮기기 위해서는 seek(long pos)나 skipBytes(int n)을 사용하면 된다.

*사실 모든 입출력 클래스들은 포인터를 내부적으로 갖고 있다. 다만 이들은 사용자 임의로 위치를 변경할 수 없다*



#### File

---

자바에선 File클래스를 통해 파일과 디렉토리를 다룰 수 있도록 하고 있다. 그래서 File인스턴스는 파일 일 수도 있고 디렉토리일 수도 있다.





## 직렬화(Serialization)



#### 직렬화란 ?

---

직렬화란 객체를 데이터 스트림으로 만드는 것을 뜻한다. 다시 얘기하면 객체에 저장된 데이터를 스트림에 쓰기위해 연속적인 데이터로 변환하는 것을 말한다.
반대로 스트림으로부터 데이터를 읽어서 객체를 만드는 것을 역직렬화(deserialization)라고 한다.

객체는 클래스에 정의된 인스턴스 변수의 집합이다. 객체에는 클래스변수나 메소드가 포함되지 않는다. 객체는 오직 인스턴스변수들로만 구성되어 있다. 인스턴스변수는 인스턴스마다 다른 값을 가질 수 있어야하기 때문에 별도의 메모리공간이 필요하지만 메소드는 변하는 것이 아니기때문에 메모리를 낭비할 필요가 없기 때문이다.

따라서 객체를 저장한다는 것은 객체의 모든 인스턴스 변수의 값을 저장한다는 것과 같은 의미이다.  어떠한 객체를 저장하고자 하면, 현재 객체의 모든 인스턴스 변수의 값을 저장하기만 하면 된다. 그리고 저장된 객체를 다시 생성하려면 저장했던 값을 읽어서 생성한 객체의 인스턴스 변수에 저장하면 되는것이다.



#### ObjectInputStream & ObjectOutputStream

---

직렬화에는 ObjectOutputStream을 사용하고 역직렬화는 ObjectInputStream을 사용한다.

이들은 각각 InputStream, OutputStream을 직접 상속받지만 기반스트림을 필요로하는 보조스트림이다.

이들은 직렬화를 위한 여러가지 메소드를 가지고 있으며, 그 중엔 defaultReadObject()와 defaultWriteObject() 라는 자동 직렬화를 수행하는 메소드도 포함되어있다.
객체를 직렬화/역직렬화 하는 작업은 객체의 모든 인스턴스변수가 참조하고 있는 모든 객체에 대한 것이기 때문에 상당히 복잡하며 시간도 오래걸린다. 따라서 자동직렬화가 편리하기는 하지만 직렬화작업시간을 단축시키려면 직렬화하고자 하는 객체의 클래스에 추가적으로 다음과 같은 2개의 메소드를 직접 구현해주어야 한다.

``` java
private void writeObject(ObjectOutputStream out) throws IOException{};
// write 메소드를 사용하여 직렬화를 수행
private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {}
// read 메소드를 사용하여 역직렬화 수행
```



#### 직렬화가 가능한 클래스 만들기 - Serializable, transient

---

직렬화하고자 하는 클래스를 java.io.Serializable 인터페이스를 구현하도록 하면 클래스의 직렬화를 가능하게 할 수 있다.

Serializable 인터페이스는 아무런 내용도 없는 빈 인터페이스이지만, 직렬화를 고려하여 작성한 클래스인지를 판단하는 기준이 된다.

transient는 직렬화 대상에서 제외되도록 만드는 키워드이다. transient가 붙은 인스턴스 변수의 값은 그 타입의 기본값으로 직렬화된다.

객체를 역직렬화 할 때는 직렬화할 때의 순서와 일치해야 한다. 따라서 직렬화할 객체가 많을 때는 각 객체를 개별적으로 직렬화하는 것보다 ArrayList와 같은 컬렉션에 저장하여 직렬화하는 것이 좋다.
역직렬화할 때 ArrayList 하나만 역직렬화 하면 되므로 역직렬화할 객체의 순서를 고려하지 않아도 되기 때문이다.



#### 직렬화가능한 클래스의 버전관리

---

직렬화된 객체를 역직렬화할 때는 직렬화 했을 때와 같은 클래스를 사용해야 한다. 그러나 클래스의 이름이 같아도 클래스의 내용이 변경된 경우 역직렬화는 실패하며 예외를 발생시킨다.

객체가 직렬화될 때 클래스에 정의된 멤버들의 정보를 이용하여 serialVersionUID라는 클래스의 버전을 자동생성하여 직렬화 내용에 포함한다.
그래서 역직렬화 할 때 클래스 버전을 대조할 수 있는 것이다.

하지만 static변수, 상수, transient 인스턴스 변수가 추가되는 경우엔 직렬화에 영향을 미치지 않기 때문에 클래스의 버전을 다르게 인식하도록 할 필요는 없다.
이럴 때는 클래스의 버전을 수동으로 관리해줄 필요가 있다.

``` java
class MyData implements Serializable{
    int value;
}
```

위와 같은 클래스가 있을때, 클래스의 버전을 수동으로 관리하려면 다음과 같이 serialVersionUID를 추가로 정의해주면 된다.

``` java
class Mydata implements Serializable{
    static final long serialVersionUID = 123432524236243424L;
    int value;
}
```

이렇게 해주면 클래스의 내용이 바뀌더라도 클래스의 버전이 자동생성된 값으로 변경되지 않는다.

serialVersionUID의 값은 정수값이면 어떠한 값으로도 지정할 수 있지만, 서로 다른 클래스간에 같은 값을 가지지 않도록 serialver.exe 를 사용하여 생성된 값을 사용하는게 보통이다.

serialver.exe는 클래스의 멤버들에 대한 정보를 바탕으로 하기때문에 이 정보가 변경되지 않는 한 항상 같은 값을 생성한다.