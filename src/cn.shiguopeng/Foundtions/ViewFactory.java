package cn.shiguopeng.Foundtions;

import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public abstract class ViewFactory {

    protected ControllerFactory controller;
    protected Stage stage;

    public ViewFactory(Stage stage, ControllerFactory controllerFactory) {

        // 全局设置
        stage.setTitle("注册窗口");
        stage.setWidth(600);
        stage.setHeight(400);

        this.stage = stage;

        try {

            Field controllerField = this.getClass().getDeclaredField("controller");
            controllerField.setAccessible(true);
            controllerField.set(this, controllerFactory);

        } catch (NoSuchFieldException e) {

            Logger.getGlobal().info(this.getClass().getName() + "视图没有控制器");
        } catch (IllegalAccessException | IllegalArgumentException e) {

            Logger.getGlobal().info(this.getClass().getName() + "视图无法设置控制器");
        }

        make();
    }

    public abstract void make();
}
