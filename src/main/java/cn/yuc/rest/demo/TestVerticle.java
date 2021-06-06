package cn.yuc.rest.demo;

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

public class TestVerticle extends AbstractVerticle {

    public Object paramsToJavaObject(MultiMap params, Class clazz) {
        Map<String, Object> map = new HashMap<>();
        for(Map.Entry<String,String> entry : params.entries()) {
            map.put(entry.getKey(),entry.getValue());
        }
        return JSON.parseObject(JSON.toJSONString(map),clazz);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        for(int i=0;i<10;++i){
            router.route("/"+i)
                    .handler(routingContext -> {
                        HttpServerRequest request = routingContext.request();
                        MultiMap params = request.params();
                        Object bt =  this.paramsToJavaObject(params,BeanTest.class);
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
