package cn.yuc.rest.demo.web.router.impl;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.AbstractRoutes;
import cn.yuc.rest.demo.web.router.Route;

import java.lang.reflect.Method;
import java.util.List;

public class UserDefineRoutes extends AbstractRoutes {

    public UserDefineRoutes() {
        super();
    }

    /**
     * 添加路由的方法
     *
     * @param route
     * @return
     */
    public void addRoute(String uri, Route route) {
        routeMap.put(uri, route);
    }


    /**
     * 获取所有路由
     */
    public void getAllRoutes() {
        List<Class<?>> classList = ProjectConfig.getList(ConfigEnum.CLASS_LIST);
        classList.forEach(clazz -> {
            // 当前遍历到的类非UdrController的子类时，跳过
            if (!UdrController.class.isAssignableFrom(clazz) || UdrController.class.equals(clazz)) return;
            try {
                UdrController udrController = (UdrController) Application.getBeanFactory().getBean(clazz);
                for (Method method : clazz.getDeclaredMethods()) {
                    // 只处理返回值为Route类型的函数
                    if (!Route.class.equals(method.getReturnType())) continue;
                    method.setAccessible(true);
                    Route<?> route = (Route<?>) method.invoke(udrController);
                    this.addRoute(route.getUri(), route);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
