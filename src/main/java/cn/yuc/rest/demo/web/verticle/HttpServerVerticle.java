package cn.yuc.rest.demo.web.verticle;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.web.router.Route;
import cn.yuc.rest.demo.web.router.impl.UserDefineRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.Map;
import java.util.Objects;

/**
 * HTTP服务
 *
 * @author YuC
 */
public class HttpServerVerticle extends AbstractVerticle {

    public static <T> T paramsToJavaObject(MultiMap params, Class<T> clazz) {
        Objects.requireNonNull(params);
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<String,String> entry : params.entries()) {
            jsonObject.put(entry.getKey(),entry.getValue());
        }
        return jsonObject.mapTo(clazz);
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
                    Class<?> clazz = routeData.getParamType();
                    Object paramJavaObject =  paramsToJavaObject(request.params(),clazz);
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

        vertx.deployVerticle(HttpServerVerticle.class, deploymentOptions, asyncResult -> {
            if (asyncResult.failed()) {
                asyncResult.cause().printStackTrace();
                System.out.println("Start server failed!");
            } else {
                System.out.println("Start server success!");
            }
        });
    }

}
