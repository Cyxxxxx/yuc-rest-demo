package cn.yuc.rest.demo.test.bean;

import cn.yuc.rest.demo.annotation.Autowire;

public class B {

    @Autowire private A a;

    public void show(){
        System.out.println(a);
    }

}
