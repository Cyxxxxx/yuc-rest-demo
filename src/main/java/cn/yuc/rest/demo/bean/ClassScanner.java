package cn.yuc.rest.demo.bean;

import java.util.List;

/**
 * 类扫描器
 * @author YuC
 */
public interface ClassScanner {

    /**
     * 将目标包下的类收集为List
     * @return 类列表
     */
    List<Class<?>> collectClasses(List<String> packageNames) throws ClassNotFoundException;

}
