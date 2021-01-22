# DataBase



### DBMS의 개요

**DB** : 여러 장치들에서 한가지의 데이터를 공유하기 위해 존재함

-   데이터베이스를 만들 때 중복을 없애는 방법으로 결함을 줄인다

-   DB에서 데이터를 찾으려면 어떠한 데이터를 가지고 참조를 해야한다.

    -   계층형 방식 (Hierarchical DBMS)
    -   네트워크 방식 (Network DBMS)
    -   객체지향 방식 (Object-Oriented DBMS)
    -   관계형 방식 (Relational DBMS)

    **DB의 단점**

-   DB의 동시성

    -   서로 다른 두 장치에서 DB의 같은 데이터에 동시에 참조하는 것을 말함

-   성능

    -   여러 장치에서 쓰다보니 혼자서 데이터를 쓰는것 보다 성능상 안좋음

-   보안

    -   네트워크상에 존재하다보니 보안문제 있을 수 있음

따라서 위의 문제를 해결하기 위해 소프트웨어상으로 DBMS (DataBase Management System)가 존재한다

-   DBMS에게 명령을 내리는걸 질의(Query라고 한다.)
    -   DDL : create/ alter/ drop (*데이터의 구조를 정의해주는 것*)
    -   DML : select/ insert/ update/ delete (*CRUD, 데이터에 대한 작업*)
    -   DCL : grant/ revoke (*관리자에 의한 사용자의 권한 제어*)



## CRUD

-   어떤 데이터베이스를 쓰든 입 출력을 파악하는게 제일 중요
-   입력 : Create, Update, Delete
-   출력 : Read
-   이걸 합쳐서 **CRUD**라고 한다

