package cn.shiguopeng.foundtions;

import cn.shiguopeng.Main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public abstract class ModelFactory {

    private String dataFilePath;
    private File dataFile;

    public ModelFactory() {

//        try {
//
//            Method getDataFilePath = this.getClass().getDeclaredMethod("getDataFilePath");
//            String filename = (String) getDataFilePath.invoke(this);
//
//            // 获取子模型的构造方法
//            System.out.println("文件名");
//
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//
//            System.out.println(e);
//            Logger.getGlobal().info(this.getClass().getName() + " 模型没有数据文件");
//        }
    }

    public void setDataFilePath(String filename) {

       dataFilePath = Main.class.getResource("/").getPath() + filename;
    }
}
