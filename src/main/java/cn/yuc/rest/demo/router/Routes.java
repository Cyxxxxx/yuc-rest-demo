package cn.yuc.rest.demo.router;

import cn.yuc.rest.demo.BeanTest;
import io.vertx.core.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 路由
 *
 * @author YuC
 */
public class Routes {
    private Routes() {};

    private static Route[] routeArray;

    /**
     * 加载路由表
     */
    static void load() {
        // TODO 从文件中获取路由表
        // 例: 获取handler的方法
        // Method handler = Class.forName("className").getMethod("methodName",paramsClasses.class);

    }

    public static void loadTest() throws NoSuchMethodException {
        int len = 3;
        routeArray = new Route[len];
        for(int i=0;i<len;++i){
            Method testHandlerMethod = BeanTest.class.getDeclaredMethod("testHandlerMethod");
            testHandlerMethod.setAccessible(true);
            routeArray[i] = new Route("/"+i,HttpMethod.GET, testHandlerMethod);
        }
    }



    /**
     * 遍历路由
     *
     * @param action
     */
    public static void foreach(Consumer<Route> action) {
        Objects.requireNonNull(action);
        for (Route router : routeArray) {
            action.accept(router);
        }
    }

    public static void main(String[] args) {
        for (int i=0;i<5;++i){
            System.out.println(Route.builder());
            System.out.println(Route.builder().uri("" + i).build().toString());
        }
    }
}
