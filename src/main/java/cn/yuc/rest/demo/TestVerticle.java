package cn.yuc.rest.demo;

import cn.yuc.rest.demo.web.verticle.HttpServerVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class TestVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        for(int i=0;i<10;++i){
            router.route("/"+i)
                    .handler(routingContext -> {
                        HttpServerRequest request = routingContext.request();
                        MultiMap params = request.params();
                        Object bt =  HttpServerVerticle.paramsToJavaObject(params,BeanTest.class);
                        System.out.println(bt);
                        HttpServerResponse response = routingContext.response();
                        // res
                        response.putHeader("content-type", "text/html")
                                .end(bt.toString());
                    });
        }
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, res -> {
                    if (res.succeeded()) {
                        System.out.println("succe1ed!");
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

        vertx.deployVerticle(TestVerticle.class, deploymentOptions, asyncResult ->
        {
            if (asyncResult.failed())
            {
                System.out.println("Start server failed!");
            }
            else
            {
                System.out.println("Start server success!");
            }
        });
    }
}
