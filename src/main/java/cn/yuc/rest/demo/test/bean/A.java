package cn.yuc.rest.demo.test.bean;

import cn.yuc.rest.demo.annotation.Autowire;

public class A {

    @Autowire private B b;

    public void show(){
        System.out.println(b);
    }

}
