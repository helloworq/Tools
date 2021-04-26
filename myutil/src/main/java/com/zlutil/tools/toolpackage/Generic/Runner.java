package com.zlutil.tools.toolpackage.Generic;

import java.lang.reflect.Field;

/**
 * 给指定实体类应用指定方法
 */
public class Runner {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Entiy entiy = new Entiy("Alice", "18", "t3t3r33434");

        String id = "id";
        System.out.println(updateData(id));

        System.out.println(updateData(entiy, "name"));

    }

    public static String updateData(String id) {
        return "url: " + id;
    }

    public static String updateData(Object o, String field) throws NoSuchFieldException, IllegalAccessException {

        Class c = o.getClass();

        Field fields;
        fields=c.getDeclaredField(field);
        fields.setAccessible(true);
        return updateData((String)fields.get(o));
    }

}

class Entiy {
    public Entiy() {
    }

    private String name;
    private String age;
    private String id;

    public Entiy(String name, String age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entiy{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}