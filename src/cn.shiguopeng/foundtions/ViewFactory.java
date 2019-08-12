package cn.shiguopeng.foundtions;

import cn.shiguopeng.contracts.Controller;
import cn.shiguopeng.contracts.View;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class ViewFactory implements View {

    protected Controller controller;
    protected Stage stage;


    @Override
    public void setState(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void setController(Controller controller) {

        this.controller = controller;
    }

    @Override
    public void render() {

        // 全局设置
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.setTitle("通讯录");
        stage.setWidth(600);
        stage.setHeight(450);
    }
}
