package com.hanfu.product.center.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }



    //获取整个工厂对象
    public static ApplicationContext getContext(){
        return context;
    }

    //根据名称获取指定bean
    public static Object getBean(String name){
        return context.getBean(name);
    }

    //根据类型获取指定bean
    public static Object getBean(Class clazz){
        return context.getBean(clazz);
    }

    //根据名称和类型获取
    public static Object getBean(String name,Class clazz){
        return context.getBean(name,clazz);
    }
}
