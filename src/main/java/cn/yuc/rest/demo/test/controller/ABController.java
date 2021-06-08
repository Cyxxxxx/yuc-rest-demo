package cn.yuc.rest.demo.test.controller;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.test.bean.A;
import cn.yuc.rest.demo.test.bean.B;
import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.Route;
import io.vertx.core.http.HttpMethod;

public class ABController extends UdrController {
    public Route ab(){
        return of(
                "/ab",
                HttpMethod.GET,
                Void.class,
                v -> {
                    Application.getBeanFactory().getBean(A.class).show();
                    Application.getBeanFactory().getBean(B.class).show();
                    return null;
                }
        );
    }
}
