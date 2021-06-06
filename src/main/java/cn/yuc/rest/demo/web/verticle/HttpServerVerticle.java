package cn.yuc.rest.demo.web.verticle;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.web.router.Route;
import cn.yuc.rest.demo.web.router.impl.UserDefineRoutes;
import com.alibaba.fastjson.JSON;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP服务
 *
 * @author YuC
 */
public class HttpServerVerticle extends AbstractVerticle {

    public Object paramsToJavaObject(MultiMap params, Class clazz) {
        Map<String, Object> map = new HashMap<>();
        for(Map.Entry<String,String> entry : params.entries()) {
            map.put(entry.getKey(),entry.getValue());
        }
        return JSON.parseObject(JSON.toJSONString(map),clazz);
    }

    @Override
    public void start() throws Exception {
        UserDefineRoutes userDefineRoutes = Application.getBeanFactory().getBean(UserDefineRoutes.class);
        Router router = Router.router(vertx);
        userDefineRoutes.foreach(route -> this.bindRoute(router, route));
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(
                        // 端口号监听
                        ProjectConfig.getInt(ConfigEnum.SERVER_PORT),
                        res -> {
                            if (res.succeeded()) {
                                System.out.println("succeed!");
                            } else {
                                System.out.println("err!");
                            }
                        });
    }

    private void bindRoute(Router router, Route routeData) {
        router.route(routeData.getHttpMethod(), routeData.getUri())
                .handler(routingContext -> {
                    HttpServerRequest request = routingContext.request();
                    Class clazz = routeData.getParamType();
                    Object paramJavaObject =  this.paramsToJavaObject(request.params(),clazz);
                    Object result = routeData.getHandler().apply(paramJavaObject);
                    // res
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "text/html")
                            .end(result.toString());
                });
    }

    public static void run() {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions()
                .setWorker(true)
                .setInstances(2)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("vertx-thread-pool");

        vertx.deployVerticle(HttpServerVerticle.class, deploymentOptions, asyncResult ->
        {
            if (asyncResult.failed()) {
                asyncResult.cause().printStackTrace();
                System.out.println("Start server failed!");
            } else {
                System.out.println("Start server success123!");
            }
        });
    }

    public static void main(String[] args) {
        run();
    }

}
