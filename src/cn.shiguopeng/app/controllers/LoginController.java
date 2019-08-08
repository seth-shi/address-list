package cn.shiguopeng.app.controllers;

import cn.shiguopeng.Foundtions.ControllerFactory;
import cn.shiguopeng.Foundtions.ViewFactory;
import cn.shiguopeng.app.views.LoginView;
import cn.shiguopeng.app.views.RegisterView;
import cn.shiguopeng.services.Encrypt;
import cn.shiguopeng.services.UsersManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController extends ControllerFactory {

    protected LoginView view;

    @Override
    public void start(Stage stage) throws Exception {

        super.start(stage);
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

    public EventHandler<ActionEvent> loginEvent(TextField usernameInput, TextField passwordInput) {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String username = usernameInput.getText();
                String password = passwordInput.getText();

                UsersManager usersManager = (UsersManager) cn.shiguopeng.Application.makeObject(UsersManager.class);


                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (! usersManager.has(username)) {

                    alert.setContentText("无效的用户名");
                    alert.show();
                    return;
                }


                // 密码加密解码
                String dbPassword = ((Encrypt)cn.shiguopeng.Application.makeObject(Encrypt.class)).decrypt(usersManager.get(username));
                if (dbPassword == null || ! dbPassword.equals(password)) {

                    alert.setContentText("密码错误");
                    alert.show();
                    return;
                }

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
                alert.setContentText("登录成功");
                alert.showAndWait();
            }
        };
    }
}
