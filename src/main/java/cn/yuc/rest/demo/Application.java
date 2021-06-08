package cn.yuc.rest.demo;

import cn.yuc.rest.demo.bean.BeanFactory;
import cn.yuc.rest.demo.bean.impl.DefaultBeanFactory;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.web.router.Route;
import cn.yuc.rest.demo.web.router.impl.UserDefineRoutes;
import cn.yuc.rest.demo.web.verticle.HttpServerVerticle;

import java.util.Map;

public class Application {

    private static BeanFactory beanFactory;
    private static UserDefineRoutes userDefineRoutes;

    static {
        try {
            beanFactory = new DefaultBeanFactory();
            userDefineRoutes = beanFactory.getBean(UserDefineRoutes.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static BeanFactory getBeanFactory(){
        return beanFactory;
    }

    public static void run() {
        userDefineRoutes.getAllRoutes();
        Map<String, Route> routeMap = ProjectConfig.getMap(ConfigEnum.ROUTE_MAP);
        System.out.println(routeMap.get("/sum").toString());
        HttpServerVerticle.run();
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        ClassScanner cs = new DefaultClassScanner();
//        List<String> packageNames = new ArrayList<>();
//        ConfigLoader.loadConfig();
//        packageNames.add(ProjectConfig.getString(ConfigEnum.ROOT_PACKAGE));
//        ProjectConfig.getInstance().put(ConfigEnum.CLASS_LIST,cs.collectClasses(packageNames));
//        UserDefineRoutes udr = new UserDefineRoutes();
//        List<UserDefineRoutes> udrSubBean = udr.getAllSubBean(ProjectConfig.getList(ConfigEnum.CLASS_LIST, Class.class));
//        udrSubBean.forEach(System.out::println);
        run();
    }
}
