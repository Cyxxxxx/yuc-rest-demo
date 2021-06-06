package cn.yuc.rest.demo.bean;

import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ConfigLoader;
import cn.yuc.rest.demo.conf.ProjectConfig;
import cn.yuc.rest.demo.bean.impl.DefaultClassScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IOC容器
 */
public abstract class AbstractBeanFactory implements BeanFactory{

    protected Map<String,Class> beanNameToClassMap = new ConcurrentHashMap<>(64);

    /** 使用 ConcurrentHashMap 时，value 不能为空 */
    protected Map<Class,Object> singletonBeanMap = new ConcurrentHashMap<>(64);

    public AbstractBeanFactory() throws ClassNotFoundException {
        ClassScanner cs = new DefaultClassScanner();
        // 加载默认配置
        ConfigLoader.loadConfig();
        // 获取包扫描所需的包列表
        List<String> packageNames = new ArrayList<>();
        packageNames.add(ProjectConfig.getString(ConfigEnum.ROOT_PACKAGE));
        // 包扫描
        ProjectConfig.getInstance().put(ConfigEnum.CLASS_LIST,cs.collectClasses(packageNames));
    }

    /**
     * 建立 Bean Name -> Class 映射
     */
    protected void setBeanNameToClassMap() {}

    @Override
    public Object getBean(String beanName) {
        return getBean(beanNameToClassMap.get(beanName));
    }

    @Override
    public <T>T getBean(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        T bean = (T) singletonBeanMap.get(clazz);
        if (bean!=null) {
            return bean;
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            bean = constructor.newInstance();
            singletonBeanMap.put(clazz,bean);
        } catch (NoSuchMethodException e) {
            System.out.println("Bean must have no-parameter constructor!");
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
