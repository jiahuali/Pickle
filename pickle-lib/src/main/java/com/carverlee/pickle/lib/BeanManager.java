package com.carverlee.pickle.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author carverLee
 * 2019/12/10 17:39
 */
public class BeanManager {
    private Map<Class, Map<String, Object>> map = new HashMap<>();
    private boolean init = false;

    @SuppressWarnings("unchecked")
    public <T> T get(Class clazz) {
        Map<String, Object> clazzMap = map.get(clazz);
        if (clazzMap == null || clazzMap.isEmpty()) {
            try {
                Object obj = getImplementation(clazz, "").newInstance();
                put(obj);
                return (T) obj;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("implementation contains error:" + clazz.getCanonicalName());
            }
        }
        return (T) clazzMap.get(clazzMap.keySet().iterator().next());
    }

    public void put(Object obj) {
        put(UUID.randomUUID().toString(), obj);
    }

    public void put(String key, Object obj) {
        Map<String, Object> beanMap = map.get(obj.getClass());
        if (beanMap == null) {
            beanMap = new HashMap<>();
        }
        beanMap.put(key, obj);
        map.put(obj.getClass(), beanMap);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class clazz, String name) {
        if (name == null || name.equals("")) {
            return get(clazz);
        }
        Map<String, Object> clazzMap = map.get(clazz);
        if (clazzMap == null || clazzMap.isEmpty() || clazzMap.get(name) == null) {
            try {
                Object obj = getImplementation(clazz, name).newInstance();
                put(name, obj);
                return (T) obj;
            } catch (Exception e) {
                System.out.println("异常，无法实例化");
                e.printStackTrace();
                throw new RuntimeException("no injection for " + clazz.getName());
            }
        }
        return (T) clazzMap.get(name);
    }

    private ArrayList<String> classList;
    private Map<String, String> classMap;

    @SuppressWarnings("unchecked")
    private Class getImplementation(Class clazz, String key) {
        try {
            if (!init) {
                Class pickleInjectionClazz = Class.forName("com.carverlee.pickle.PickleInjection");
                Field classListField = pickleInjectionClazz.getField("classList");
                Field classMapField = pickleInjectionClazz.getField("classMap");
                Object pickleInjection = pickleInjectionClazz.newInstance();
                classList = (ArrayList<String>) classListField.get(pickleInjection);
                classMap = (HashMap<String, String>) classMapField.get(pickleInjection);
                init = true;
            }
            if (key == null || key.isEmpty()) {
                return getImplFromList(clazz);
            } else {
                return getImplFromMap(clazz, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PickleInjection not found");
        }
    }

    private Class getImplFromList(Class clazz) {
        try {
            for (String clazzImplName : classList) {
                Class clazzImpl = Class.forName(clazzImplName);
                Class[] interfaces = clazzImpl.getInterfaces();
                if (interfaces == null || interfaces.length == 0) {
                    continue;
                }
                for (Class interfaze : interfaces) {
                    if (interfaze == clazz) {
                        return clazzImpl;
                    }
                }
            }
            throw new RuntimeException("dependency not found:" + clazz.getCanonicalName());
        } catch (Exception e) {
            throw new RuntimeException("PickleInjection not found in list");
        }
    }

    private Class getImplFromMap(Class clazz, String key) {
        String implClazzName = classMap.get(key);
        if (implClazzName == null) {
            throw new RuntimeException("no implementation of " + clazz + ",key:" + key);
        }
        try {
            Class clazzImpl = Class.forName(implClazzName);
            Class[] interfaces = clazzImpl.getInterfaces();
            if (interfaces == null || interfaces.length == 0) {
                throw new RuntimeException("no implementation of " + clazz + ",key:" + key);
            }
            for (Class interfaze : interfaces) {
                if (interfaze == clazz) {
                    return clazzImpl;
                }
            }
            throw new RuntimeException("dependency not found:" + clazz.getCanonicalName());
        } catch (Exception e) {
            throw new RuntimeException("PickleInjection not found in list");
        }
    }

}
