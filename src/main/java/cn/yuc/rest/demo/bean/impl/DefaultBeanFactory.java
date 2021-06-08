package cn.yuc.rest.demo.bean.impl;

import cn.yuc.rest.demo.bean.AbstractBeanFactory;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ProjectConfig;

/**
 * 默认Bean工厂
 * @author YuC
 */
public class DefaultBeanFactory extends AbstractBeanFactory {

    public DefaultBeanFactory() throws ClassNotFoundException {
        super();
        // 建立 Bean Name -> Class 映射
        setBeanNameToClassMap();
    }

    @Override
    protected void setBeanNameToClassMap() {
        ProjectConfig.<Class<?>>getList(ConfigEnum.CLASS_LIST)
                .forEach(clazz -> beanNameToClassMap.put(clazz.getSimpleName(),clazz));
    }


}
