package cn.yuc.rest.demo.verticle;

import cn.yuc.rest.demo.BeanTest;
import cn.yuc.rest.demo.router.Routes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.lang.reflect.InvocationTargetException;

/**
 * HTTP服务
 *
 * @author YuC
 */
public class HttpServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Routes.loadTest();
        BeanTest beanTest = new BeanTest();
        Router router = Router.router(vertx);
        Routes.foreach(r -> {
            router
                // 绑定URI以及设置HTTP方法，如GET,POST等
                .route(r.getHttpMethod(), r.getUri())
                // handler处理请求
                .handler(routingContext -> {
                    HttpServerResponse response = routingContext.response();
                    try {
                        r.getHandler()
                                .invoke(beanTest/* TODO 获取HTTP请求的参数传参 调用handler方法 */);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    // res
                    response.putHeader("content-type", "text/html")
                            .end(r.getUri());
                });
        });
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, res -> {
                    if (res.succeeded()) {
                        System.out.println("succe2ed!");
                    } else {
                        System.out.println("err!");
                    }
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions()
                .setWorker(true)
                .setInstances(2)
                .setWorkerPoolSize(2)
                .setWorkerPoolName("vertx-thread-pool");

        vertx.deployVerticle(HttpServerVerticle.class, deploymentOptions, asyncResult ->
        {
            if (asyncResult.failed())
            {
                asyncResult.cause().printStackTrace();
                System.out.println("Start server failed!");
            }
            else
            {
                System.out.println("Start server success123!");
            }
        });
    }

}
