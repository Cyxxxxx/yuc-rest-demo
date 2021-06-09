package cn.yuc.rest.demo.test.controller;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.annotation.Autowire;
import cn.yuc.rest.demo.test.bean.A;
import cn.yuc.rest.demo.test.bean.B;
import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.Route;
import io.vertx.core.http.HttpMethod;

public class ABController extends UdrController {


    @Autowire
    private A a;

    public Route ab(){
        return of(
                "/ab",
                HttpMethod.GET,
                Void.class,
                v -> {
                    try {
                        a.show();
                        Application.getBeanFactory().getBean(B.class).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
    }
}
