package cn.shiguopeng.controlllers;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("登录窗口");
        stage.setWidth(600);
        stage.setHeight(400);
        // stage.getIcons().add(new Image("file:/src/resources/icon.png"));

        // 用户名和密码
        Label usernameLabel = new Label("账号");
        Label passwordLabel = new Label("密码");
        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Label registerLabel = new Label("还没有账号,去注册");
        Button loginBtn = new Button("登录");

        GridPane gridpane = new GridPane();
        gridpane.add(usernameLabel, 0 ,0);
        gridpane.add(usernameInput, 1 ,0);
        gridpane.add(passwordLabel, 0 ,1);
        gridpane.add(passwordInput, 1 ,1);
        gridpane.add(registerLabel, 0 ,2);
        gridpane.add(loginBtn, 1 ,2);

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

                System.out.println(actionEvent.getEventType());
            }
        });

        stage.show();
    }
}
