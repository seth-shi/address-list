package cn.shiguopeng.views;

import cn.shiguopeng.services.UsersManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.setTitle("登录窗口");
        stage.setWidth(600);
        stage.setHeight(400);

        // 用户名和密码
        Text welcomeTxt = new Text("欢迎使用通讯录");
        Label usernameLabel = new Label("账号");
        Label passwordLabel = new Label("密码");
        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Label registerLabel = new Label("还没有账号,去注册");
        Button loginBtn = new Button("登录");

        GridPane gridpane = new GridPane();
        gridpane.add(welcomeTxt, 0 ,0);
        gridpane.add(usernameLabel, 0, 1);
        gridpane.add(usernameInput, 1 ,1);
        gridpane.add(passwordLabel, 0 ,2);
        gridpane.add(passwordInput, 1 ,2);
        gridpane.add(registerLabel, 0 ,3);
        gridpane.add(loginBtn, 1 ,3);

        gridpane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        gridpane.setHgap(20);
        gridpane.setVgap(20);

        Scene scene = new Scene(gridpane);
        stage.setScene(scene);

        registerLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // 跳转去注册页面
                try {
                    new Register().start(new Stage());
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

                UsersManager usersManager = new UsersManager();

                FadeTransition ft = new FadeTransition(Duration.millis(1000), welcomeTxt);
                ft.setFromValue(0.1);
                ft.setToValue(1);
                ft.play();

                if (! usersManager.has(username)) {


                    welcomeTxt.setText("无效的用户名");
                    return;
                }

                String dbPassword = usersManager.get(username);
                if (! dbPassword.equals(password)) {

                    welcomeTxt.setText("密码错误");
                    return;
                }

                welcomeTxt.setText("登录成功");
            }
        });

        stage.show();
    }
}
