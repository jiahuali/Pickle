package com.carverlee.pickle.lib;

/**
 * @author carverLee
 * 2020/5/12 14:56
 */
public class KeyClass {
    private String key;
    private Class<? extends Injectable> clazz;

    public KeyClass(String key, Class<? extends Injectable> clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    String getKey() {
        return key;
    }

    Class<? extends Injectable> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "KeyClass{" +
                "key='" + key + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
