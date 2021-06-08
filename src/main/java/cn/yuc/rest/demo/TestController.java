package cn.yuc.rest.demo;

import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.Route;
import io.vertx.core.http.HttpMethod;


public class TestController extends UdrController {

    public Route<?> twoSum() {
        return of(
                "/sum",
                HttpMethod.GET,
                // 入参类型
                BeanTest.class,
                // 业务处理函数
                param -> param.getA() + param.getB()
        );
    }

}
