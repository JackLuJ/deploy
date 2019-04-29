package com.jackluan.deploy;

import com.jackluan.deploy.virtualMachine.entity.VMEntity;

import java.lang.reflect.Method;
import java.util.UUID;

public class Test {

    public void create(VMEntity vmEntity) {
//        VMEntity vmEntity = new VMEntity();
        vmEntity.setId("1111");
    }

    public void create2() throws Exception {
        Class clazz = Class.forName("com.jackluan.deploy.virtualMachine.entity.VMEntity");
        Object obj = clazz.getDeclaredConstructor().newInstance();
//        clazz.getDeclaredMethod("setId", String.class).invoke(obj, "1111");
    }

    public void create3(ClassLoader classLoader) throws Exception{
        Object obj = classLoader.loadClass("com.jackluan.deploy.virtualMachine.entity.VMEntity");
    }

    public static void main(String[] args) throws Exception {
        Test t = new Test();
//        VMEntity vmEntity = new VMEntity();
        Class clazz = Class.forName("com.jackluan.deploy.virtualMachine.entity.VMEntity");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            t.create3(classLoader);
        }
        long end = System.currentTimeMillis();
        long a =end - start;
        System.out.println(a);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            t.create2();
        }
        end = System.currentTimeMillis();
        long b =end - start;
        System.out.println(b);

        System.out.println(b/a);
    }

}
