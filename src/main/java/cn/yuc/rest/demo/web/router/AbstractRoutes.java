package cn.yuc.rest.demo.web.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 抽象路由表
 * @author YuC
 */
public abstract class AbstractRoutes {
    protected static List<Route> routeList;

    /**
     * 路由的加载方法
     *
     * @throws NoSuchMethodException
     */
    public void load() {
        if(routeList == null) {
            routeList = new ArrayList<>();
        }
    }



    /**
     * 遍历路由方法
     *
     * @param action
     */
    public final void foreach(Consumer<Route> action) {
        Objects.requireNonNull(action);
        for (Route router : routeList) {
            action.accept(router);
        }
    }


}
