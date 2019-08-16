package cn.shiguopeng.foundtions;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewFactory extends Application {

    protected Stage stage;


    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        // 全局设置
        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.setTitle("通讯录");
        stage.setWidth(800);
        stage.setHeight(600);
    }
}
