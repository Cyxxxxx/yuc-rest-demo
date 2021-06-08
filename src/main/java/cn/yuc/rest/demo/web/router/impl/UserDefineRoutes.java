package cn.yuc.rest.demo.web.router.impl;

import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.web.UdrController;
import cn.yuc.rest.demo.web.router.AbstractRoutes;
import cn.yuc.rest.demo.web.router.Route;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
     * 获取所有继承UDR类的对象
     * @return
     */
    public List<UserDefineRoutes> getAllSubBean(List<Class> classList){
        List<UserDefineRoutes> udrList = new ArrayList<>();
        classList.forEach(clazz -> {
            if(!this.getClass().isAssignableFrom(clazz) || this.getClass().equals(clazz)) return;
            try {
                Constructor<UserDefineRoutes> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                UserDefineRoutes udr = constructor.newInstance();
                udrList.add(udr);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return udrList;
    }

    /**
     * 获取所有路由
     */
    public void getAllRoutes() {
        List<Class> classList = ProjectConfig.getList(ConfigEnum.CLASS_LIST);
        classList.forEach(clazz -> {
            if(!UdrController.class.isAssignableFrom(clazz) || UdrController.class.equals(clazz)) return;
            try {
                Constructor<UdrController> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                UdrController udrController = constructor.newInstance();
                Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> Route.class.equals(method.getReturnType()))
                        .forEach(method -> {
                            method.setAccessible(true);
                            try {
                                Route route = (Route) method.invoke(udrController);
                                this.addRoute(route.getUri(), route);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
