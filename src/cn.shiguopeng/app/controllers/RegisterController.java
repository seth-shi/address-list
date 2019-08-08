package cn.shiguopeng.app.controllers;

import cn.shiguopeng.app.views.LoginView;
import cn.shiguopeng.app.views.RegisterView;
import cn.shiguopeng.contracts.ViewInterface;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.services.Encrypt;
import cn.shiguopeng.services.UsersManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterController extends Application {


    private ViewInterface view;
    private Stage stage;

    public RegisterController() {

        this.view = new RegisterView(this);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        this.view.make(this.stage);

    }

    public EventHandler<MouseEvent> gotoLoginEvent() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // 跳转去登录界面
                try {

                    new LoginController().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 关闭当前窗口
                stage.close();
            }
        };
    }

    public EventHandler<ActionEvent> registerEvent(TextField usernameInput, TextField passwordInput, TextField confirmPasswordInput) {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String username = usernameInput.getText();
                String password = passwordInput.getText();
                String confirmPassword = confirmPasswordInput.getText();
                UsersManager usersManager = (UsersManager) cn.shiguopeng.Application.makeObject(UsersManager.class);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (! password.equals(confirmPassword)) {

                    alert.setContentText("两次密码不一致");
                    alert.show();
                    return;
                }

                if (usersManager.has(username)) {

                    alert.setContentText("用户名已经存在");
                    alert.show();
                    return;
                }

                if (password.length() < 4) {

                    alert.setContentText("请把密码设置得复杂一点");
                    alert.show();
                    return;
                }

                // 用户名和密码都不能包含 =
                if (username.contains(StoreOptionEnum.SEPARATOR)) {

                    alert.setContentText("用户名不能包含" + StoreOptionEnum.SEPARATOR + "符号");
                    alert.show();
                    return;
                }

                if (password.contains(StoreOptionEnum.SEPARATOR)) {

                    alert.setContentText("密码不能包含" + StoreOptionEnum.SEPARATOR + "符号");
                    alert.show();
                    return;
                }


                usersManager.put(username, password);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
                alert.setContentText("注册成功,请去登录吧");
                alert.showAndWait();
            }
        };
    }
}
