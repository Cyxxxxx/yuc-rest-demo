package cn.yuc.rest.demo.web.router;

import cn.yuc.rest.demo.web.result.ResultUtil;
import io.vertx.core.http.HttpMethod;

import java.util.function.Function;

/**
 * 路由实体类
 *
 * @author YuC
 */
public class Route<T> {
    private String uri;
    private HttpMethod httpMethod;
    private Class<T> paramType;
    private Function<T, ResultUtil.Result> handler;


    public Route(String uri, HttpMethod httpMethod, Class<T> paramType, Function<T, Object> handler) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.paramType = paramType;
        this.handler = handler.andThen(ResultUtil::ok);
    }


    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Function<T, ResultUtil.Result> getHandler() {
        return handler;
    }

    public Class<T> getParamType() {
        return paramType;
    }

    @Override
    public String toString() {
        return "Route{" +
                "uri='" + uri + '\'' +
                ", httpMethod=" + httpMethod +
                ", paramType=" + paramType +
                ", handler=" + handler +
                '}';
    }

//    /**
//     * Builder模式
//     * @return a Builder instance
//     */
//    public static <T> Builder<T> builder(){return Builder.getInstance();}
//    public static class Builder<T> {
//        private String uri;
//        private HttpMethod httpMethod;
//        private Class<T> clazz;
//        private Function<T, ResultUtil.Result<T>> handler;
//
//        /**
//         * Singleton
//         */
//        private static Builder instance = new Builder();
//        static <T> Builder<T> getInstance(){
//            return instance;
//        }
//
//        public <T> Builder<T> uri(String uri) {
//            this.uri = uri;
//            return (Builder<T>) this;
//        }
//
//        public <T> Builder<T> method(HttpMethod httpMethod) {
//            this.httpMethod = httpMethod;
//            return (Builder<T>) this;
//        }
//
//        public <T> Builder<T> clazz(Class<T> clazz) {
//            this.clazz = clazz;
//            return (Builder<T>) this;
//        }
//
//        public <T> Builder<T> handler(Function<T, ResultUtil.Result<T>> handler) {
//            this.handler = handler;
//            return (Builder<T>) this;
//        }
//
//        public Route<T> build() {
//            return new Route<T>(this.uri, this.httpMethod, this.clazz, this.handler);
//        }
//    }
}
