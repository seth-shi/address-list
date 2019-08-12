package cn.shiguopeng.app.controllers;

import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.app.views.HomeView;
import cn.shiguopeng.app.views.LoginView;
import cn.shiguopeng.contracts.View;
import cn.shiguopeng.foundtions.ControllerFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController extends ControllerFactory {


    @Override
    public void start(Stage stage) throws Exception {

        renderView(stage);
    }


    public EventHandler<MouseEvent> gotoRegisterEvent() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // 跳转去注册页面
                try {
                    new RegisterController().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 关闭当前窗口
                stage.close();
            }
        };
    }

    public EventHandler<ActionEvent> addContactAction() {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("点击添加联系人");

                Stage addStage = new Stage(StageStyle.UNIFIED);
                addStage.initOwner(stage);
                addStage.show();
            }
        };
    }

    @Override
    public View makeView() {

        return new HomeView();
    }
}
