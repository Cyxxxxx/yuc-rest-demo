package cn.yuc.rest.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

public class TestVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        for(int i=0;i<1000;++i){
            router.route("/"+i)
                    .handler(routingContext -> {
                        HttpServerResponse response = routingContext.response();
                        // data
                        Map<String,String> map = new HashMap<>();
                        map.put("data","abc");
                        // res
                        response.putHeader("content-type", "text/html")
                                .end(map.toString());
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
