package cn.yuc.rest.demo;

import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.Route;
import io.vertx.core.http.HttpMethod;


public class TestController extends UdrController {


    public Route twoSum() {
        return of(
                "/sum",
                HttpMethod.GET,
                BeanTest.class,
                param -> param.getA() + param.getB()
        );
    }
}
