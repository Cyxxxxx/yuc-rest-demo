package cn.yuc.rest.demo.bean.impl;

import cn.yuc.rest.demo.Application;
import cn.yuc.rest.demo.bean.ClassScanner;
import cn.yuc.rest.demo.conf.ConfigEnum;
import cn.yuc.rest.demo.conf.ConfigLoader;
import cn.yuc.rest.demo.conf.ProjectConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultClassScanner implements ClassScanner {

    /**
     * 获取项目根目录
     *
     * @return
     */
    String getProjectRootPath() {
        // TODO 先这么写，等以后写成框架再加配置
        return Application.class.getResource("").getPath();
    }


    /**
     * 遍历收集涉及包中的所有类
     *
     * @param file
     * @param classList
     * @param basePackagePath
     * @throws ClassNotFoundException
     */
    void findClasses(File file, List<Class<?>> classList, String basePackagePath) throws ClassNotFoundException {
        Objects.requireNonNull(file);
        if (file.isDirectory()) {
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                findClasses(subFile, classList, basePackagePath);
            }
            return;
        }
        String fileName = file.getName();
        if (fileName.endsWith(".class")) {
            String path = file.getPath();
            String className = path.substring(path.indexOf(basePackagePath))
                    .replace(".class", "")
                    .replace(File.separator, ".");
            classList.add(Class.forName(className));
        }
    }


    @Override
    public List<Class<?>> collectClasses(List<String> packageNames) throws ClassNotFoundException {
        List<Class<?>> result = new ArrayList<>();
        for (String packageName : packageNames) {
            String packagePath = packageName.replace(".", File.separator);
            findClasses(new File(getProjectRootPath()), result, packagePath);
        }
        return result;
    }



    public static void main(String[] args) throws ClassNotFoundException {
        ClassScanner cs = new DefaultClassScanner();
        List<String> packageNames = new ArrayList<>();
        ConfigLoader.loadConfig();
        packageNames.add(ProjectConfig.getString(ConfigEnum.ROOT_PACKAGE));
        System.out.println(cs.collectClasses(packageNames));
    }
}
