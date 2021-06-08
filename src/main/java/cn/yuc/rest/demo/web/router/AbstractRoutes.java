package cn.yuc.rest.demo.web.router;

import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 抽象路由表
 * @author YuC
 */
public abstract class AbstractRoutes {
    protected static Map<String,Route> routeMap;

    public AbstractRoutes() {
        load();
    }

    /**
     * 路由的加载方法
     *
     * @throws NoSuchMethodException
     */
    public void load() {
        if(routeMap == null) {
            routeMap = new ConcurrentHashMap<>(64);
            ProjectConfig.getInstance().put(ConfigEnum.ROUTE_MAP,routeMap);
        }
    }



    /**
     * 遍历路由方法
     *
     * @param action
     */
    public final void foreach(Consumer<Route> action) {
        Objects.requireNonNull(action);
        for (Route router : routeMap.values()) {
            action.accept(router);
        }
    }


}
