package cn.yuc.rest.demo.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 项目配置
 *
 * @author YuC
 */
public class ProjectConfig {

    private static Map<ConfigEnum, Object> config = new HashMap<>();

    private ProjectConfig() {
    }

    private static ProjectConfig instance = new ProjectConfig();

    public static ProjectConfig getInstance(){
        return instance;
    }

    /**
     * 提供put方法的链式调用
     *
     * @param key
     * @param value
     * @return
     */
    public ProjectConfig put(ConfigEnum key, Object value) {
        config.put(key, value);
        return instance;
    }


    public static Object get(ConfigEnum key) {
        Objects.requireNonNull(key);
        return config.get(key);
    }

    public static String getString(ConfigEnum key) {
        return String.valueOf(get(key));
    }

    public static int getInt(ConfigEnum key) {
        return Integer.parseInt(getString(key));
    }

    public static <T>List <T>getList(ConfigEnum key,Class<T> clazz) {
        Object value = get(key);
        return (List<T>) value;
    }
}
