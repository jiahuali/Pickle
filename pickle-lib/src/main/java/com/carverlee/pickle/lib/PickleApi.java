package com.carverlee.pickle.lib;

import com.carverlee.pickle.annotation.Autowired;

import java.lang.reflect.Field;

/**
 * @author carverLee
 * 2019/12/17 11:53
 */
public class PickleApi {
    private static final BeanManager beanManager = new BeanManager();

    /**
     * provide injections with autoWired annotation
     * this injection with reflection will cost times
     *
     * @param obj object to be injected
     */
    public static void inject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, beanManager.get(field.getType(), autowired.value()));
                    field.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("inject failed :" + obj);
                }
            }
        }
    }

    /**
     * provide injection with bridge
     *
     * @param bridge           bridge
     * @param injectionClasses injectionClasses
     */
    @SafeVarargs
    public static void inject(InjectBridge bridge, Class<? extends Injectable>... injectionClasses) {
        if (injectionClasses == null || injectionClasses.length == 0) {
            return;
        }
        Injectable[] instances = new Injectable[injectionClasses.length];
        for (int i = 0; i < injectionClasses.length; i++) {
            instances[i] = beanManager.get(injectionClasses[i]);
        }
        bridge.inject(instances);
    }

    /**
     * provide injection with bridge,can assign name
     *
     * @param bridge           bridge
     * @param injectionClasses injectionClasses
     */
    public static void inject(InjectBridge bridge, KeyClass... injectionClasses) {
        if (injectionClasses == null || injectionClasses.length == 0) {
            return;
        }
        Injectable[] instances = new Injectable[injectionClasses.length];
        for (int i = 0; i < injectionClasses.length; i++) {
            instances[i] = beanManager.get(injectionClasses[i].getClazz(), injectionClasses[i].getKey());
        }
        bridge.inject(instances);
    }
}
