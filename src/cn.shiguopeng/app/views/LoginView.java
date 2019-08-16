package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.app.controllers.RegisterController;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.foundtions.ControllerFactory;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class LoginView extends ViewFactory {

    @Override
    public void render() {

        super.render();

        Panel panel = new Panel("欢迎使用通讯录");
        panel.getStyleClass().add("panel-info");

        // 用户名和密码
        Label usernameLabel = new Label("账号");
        Label passwordLabel = new Label("密码");

        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();

        Label registerLabel = new Label("还没有账号,去注册");
        registerLabel.getStyleClass().setAll("lbl", "lbl-default");

        Button loginBtn = new Button("登录");
        loginBtn.getStyleClass().setAll("btn","btn-success");

        GridPane gridpane = new GridPane();
        gridpane.add(usernameLabel, 0, 0);
        gridpane.add(usernameInput, 1 ,0);
        gridpane.add(passwordLabel, 0 ,1);
        gridpane.add(passwordInput, 1 ,1);
        gridpane.add(loginBtn, 1, 2);
        gridpane.add(registerLabel, 1, 3);

        gridpane.setVgap(20);
        gridpane.setHgap(20);
        gridpane.setAlignment(Pos.CENTER);


        panel.setBody(gridpane);

        Scene scene = new Scene(panel, 100, 20);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);



        // 点击注册
        registerLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        });
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String username = usernameInput.getText();
                String password = passwordInput.getText();

                UserModel inputUser = new UserModel(username, password);

                Alert alert = new Alert(Alert.AlertType.ERROR);

                UserModel dbUser = (UserModel) inputUser.first();
                if (dbUser == null) {

                    alert.setContentText("无效的用户名");
                    alert.show();
                    return;
                }

                // 密码加密解码
                if (! dbUser.is(inputUser)) {

                    alert.setContentText("密码错误");
                    alert.show();
                    return;
                }

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("登录成功");
                alert.showAndWait();
            }
        });

        stage.show();
    }
}
