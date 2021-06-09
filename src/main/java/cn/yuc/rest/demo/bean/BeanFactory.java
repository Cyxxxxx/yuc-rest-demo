package cn.yuc.rest.demo.bean;

/**
 * @author YuC
 */
public interface BeanFactory {

    /**
     * 由Bean名称获取Bean
     *
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 由Class获取Bean
     * @param clazz
     * @return
     */
    <T>T getBean(Class<T> clazz) throws Exception;

}
