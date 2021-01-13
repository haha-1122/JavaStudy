package jungsuk.eleven;

public class Eleven {

    public static void main(String[] args) {

        Tests tests = new Tests();
        System.out.println(Interface.a);
        tests.world();

    }
}

interface Interface{

    int a = 1;

    void hello();

    default void world() {
        System.out.println("world");
    }


}

class Tests implements Interface {


    @Override
    public void hello() {
        System.out.println("hello");
    }

    @Override
    public void world() {
        System.out.println("Test World");
    }





}









