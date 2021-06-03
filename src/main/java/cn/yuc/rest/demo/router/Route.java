package cn.yuc.rest.demo.router;

import io.vertx.core.http.HttpMethod;

import java.lang.reflect.Method;

/**
 * 路由实体类
 *
 * @author YuC
 */
public class Route {
    private String uri;
    private HttpMethod httpMethod;
    private Method handler;

    public Route(String uri, HttpMethod httpMethod, Method handler) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.handler = handler;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Method getHandler() {
        return handler;
    }

    @Override
    public String toString() {
        return "Route{" +
                "uri='" + uri + '\'' +
                ", httpMethod=" + httpMethod +
                ", handler=" + handler +
                '}';
    }

    /**
     * Builder模式
     * @return a Builder instance
     */
    static Builder builder(){return Builder.getInstance();}
    static class Builder {
        private String uri;
        private HttpMethod httpMethod;
        private Method handler;

        /**
         * Singleton
         */
        private static Builder instance = new Builder();
        static Builder getInstance(){
            return instance;
        }

        Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        Builder method(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        Builder handler(Method handler) {
            this.handler = handler;
            return this;
        }

        Route build() {
            return new Route(this.uri, this.httpMethod, this.handler);
        }
    }
}
