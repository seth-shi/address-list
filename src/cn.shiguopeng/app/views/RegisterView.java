package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.foundtions.ControllerFactory;
import cn.shiguopeng.foundtions.ViewFactory;
import cn.shiguopeng.app.controllers.RegisterController;
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

public class RegisterView extends ViewFactory {





    @Override
    public void render() {

        super.render();

        Panel panel = new Panel("欢迎使用通讯录");
        panel.getStyleClass().add("panel-info");

        // 用户名和密码
        Label usernameLabel = new Label("账号");
        Label passwordLabel = new Label("密码");
        Label confirmPassword = new Label("重复密码");

        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        PasswordField confirmPasswordInput = new PasswordField();

        Label loginLabel = new Label("已经有账号了,去登录");
        loginLabel.getStyleClass().setAll("lbl", "lbl-default");

        Button registerButton = new Button("注册");
        registerButton.getStyleClass().setAll("btn", "btn-primary");

        GridPane gridpane = new GridPane();
        gridpane.add(usernameLabel, 0, 0);
        gridpane.add(usernameInput, 1, 0);
        gridpane.add(passwordLabel, 0, 1);
        gridpane.add(passwordInput, 1, 1);
        gridpane.add(confirmPassword, 0, 2);
        gridpane.add(confirmPasswordInput, 1, 2);
        gridpane.add(registerButton, 1, 3);
        gridpane.add(loginLabel, 1, 4);

        gridpane.setVgap(20);
        gridpane.setHgap(20);
        gridpane.setAlignment(Pos.CENTER);


        panel.setBody(gridpane);

        Scene scene = new Scene(panel, 100, 20);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);

        // 点击注册
        loginLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        });
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String username = usernameInput.getText();
                String password = passwordInput.getText();
                String confirmPassword = confirmPasswordInput.getText();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                if (! password.equals(confirmPassword)) {

                    alert.setContentText("两次密码不一致");
                    alert.show();
                    return;
                }

                if (password.length() < 4) {

                    alert.setContentText("请把密码设置得复杂一点");
                    alert.show();
                    return;
                }

                // 用户名和密码都不能包含 =
                if (username.contains(String.valueOf(StoreOptionEnum.FILL_BLACK_MARK))) {

                    alert.setContentText("用户名不能包含" + StoreOptionEnum.FILL_BLACK_MARK + "符号");
                    alert.show();
                    return;
                }

                if (password.contains(String.valueOf(StoreOptionEnum.FILL_BLACK_MARK))) {

                    alert.setContentText("密码不能包含" + StoreOptionEnum.FILL_BLACK_MARK + "符号");
                    alert.show();
                    return;
                }

                UserModel inputUser = new UserModel(username, password);

                if (inputUser.first() != null) {

                    alert.setContentText("用户名已经存在");
                    alert.show();
                    return;
                }

                // 创建用户
                inputUser.create();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("注册成功,请去登录吧");
                alert.showAndWait();
            }
        });
        stage.show();
    }
}
