package cn.shiguopeng;

import cn.shiguopeng.contracts.ContainerContract;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

public class Container implements ContainerContract {

    protected HashMap<String, Object> instances = new HashMap<>();
    private HashSet<String> stacks = new HashSet<>();

    @Override
    public void register(Object object) {

        instances.put(object.getClass().getName(), object);
    }

    @Override
    public Object make(Class cls) {

        // 栈，防止重复调用
        stacks = new HashSet<>();

        Object newObj = resolve(cls);

        stacks.clear();

        return newObj;
    }

    public Object resolve(Class cls) {

        if (! stacks.add(cls.getName())) {

            Logger.getGlobal().info("递归调用");
            System.exit(1);
        }

        // 如果是 Java 基础类,不能实例化
        if (cls.getPackageName().equals("java.lang")) {
            return new Object();
        }


        if (instances.containsKey(cls.getName())) {

            System.out.println("已有的实例");
            return instances.get(cls.getName());
        }

        Constructor[] constructors = cls.getConstructors();

        if (constructors.length == 0) {

            return new Object();
        }

        for (Constructor constructor : constructors) {

            Class[] parameters = constructor.getParameterTypes();

            if (parameters.length == 0) {

                Object newObj = null;
                try {
                    newObj = constructor.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                instances.put(newObj.getClass().getName(), newObj);
                return newObj;
            }


            // 构造函数
            Object[] relParameters = new Object[parameters.length];
            for (int i = 0, l = parameters.length; i < l; ++ i) {

                relParameters[i] = resolve(parameters[i]);
            }

            Object newObj = null;
            try {
                newObj = constructor.newInstance(relParameters);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            instances.put(newObj.getClass().getName(), newObj);
            return newObj;
        }

        return new Object();
    }
}
