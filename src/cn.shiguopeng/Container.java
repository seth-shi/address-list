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

            return null;
        }

        // 如果已经实例化过了,那么知己返回
        if (instances.containsKey(cls.getName())) {

            return instances.get(cls.getName());
        }

        Constructor[] constructors = cls.getConstructors();

        for (Constructor constructor : constructors) {

            Class[] parameters = constructor.getParameterTypes();

            // 如果构造函数不需要参数,那么直接实例化
            if (parameters.length == 0) {

                Object newObj = createObject(constructor);

                instances.put(newObj.getClass().getName(), newObj);
                return newObj;
            }


            // 构造函数
            Object[] relParameters = new Object[parameters.length];
            boolean canCreate = true;

            for (int i = 0, l = parameters.length; i < l; ++ i) {

                Object param = resolve(parameters[i]);
                // 如果参数无法构造出来,那么跳过这个构造函数
                if (param == null) {

                    Logger.getGlobal().info("无法用此构造函数构造出" + cls.getName());
                    canCreate = false;
                    break;
                }

                relParameters[i] = param;
            }

            if (canCreate) {

                Object newObj = createObject(constructor, relParameters);
                if (newObj == null) {

                    return null;
                }

                instances.put(newObj.getClass().getName(), newObj);
                return newObj;
            }
        }

        // 如果没有构造函数,或者无法构造出来
        return null;
    }

    private Object createObject(Constructor constructor, Object[] parameters) {

        Object newObj = null;
        try {

            newObj = constructor.newInstance(parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (newObj == null) {

            return null;
        }

        return newObj;
    }

    private Object createObject(Constructor constructor) {

        return createObject(constructor, new Object[]{});
    }
}
