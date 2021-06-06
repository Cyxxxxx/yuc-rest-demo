package cn.yuc.rest.demo.web;

import cn.yuc.rest.demo.web.router.Route;
import io.vertx.core.http.HttpMethod;

import java.util.function.Function;

public class UdrController extends AbstractController {

    protected String prePath = "";


    public static <T> Route<T> of(String uri, HttpMethod httpMethod, Class<T> paramType, Function<T, Object> handler) {
        return new Route<>(uri,httpMethod,paramType,handler);
    }

}
