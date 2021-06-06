package cn.yuc.rest.demo.conf;

import java.util.ArrayList;

/**
 * 配置加载类
 * @author YuC
 */
public class ConfigLoader {
    public static void loadConfig(){
        ProjectConfig.getInstance()
                .put(ConfigEnum.SERVER_PORT,"8080")
                .put(ConfigEnum.ROOT_PACKAGE,"cn.yuc.rest.demo")
                .put(ConfigEnum.UDR_BEAN_LIST, new ArrayList<Class>());
    }

    public static void main(String[] args) {
        loadConfig();
        System.out.println(ProjectConfig.get(ConfigEnum.ROOT_PACKAGE));
    }
}
