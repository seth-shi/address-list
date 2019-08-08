package cn.shiguopeng.Foundtions;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class ControllerFactory extends Application {

    protected Stage stage;


    public void start(Stage stage) throws Exception {

        this.stage = stage;

        // 构建视图
        try {

            // 实例化视图类
            Field viewField = this.getClass().getDeclaredField("view");
            Constructor constructor = viewField.getType().getConstructor(Stage.class, ControllerFactory.class);
            constructor.newInstance(stage, this);

        } catch (NoSuchFieldException e) {

            Logger.getGlobal().info(this.getClass().getName() + " 控制器没有视图类");

        } catch (
                NoSuchMethodException |
                SecurityException  |
                InstantiationException |
                IllegalAccessException |
                IllegalArgumentException |
                InvocationTargetException e) {

            Logger.getGlobal().info(this.getClass().getName() + " 控制器无法构造出视图类 ");
        }

    }

}
