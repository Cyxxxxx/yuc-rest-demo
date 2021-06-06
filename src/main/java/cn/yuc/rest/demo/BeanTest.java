package cn.yuc.rest.demo;

public class BeanTest {
    @Override
    public String toString() {
        return "BeanTest{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    private int b;
}
