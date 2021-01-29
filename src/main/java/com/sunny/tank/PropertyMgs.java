package com.sunny.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgs {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgs.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key){
        if(props==null){
            return null;
        }
        return props.get(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertyMgs.get("initTankCount"));
    }
}
