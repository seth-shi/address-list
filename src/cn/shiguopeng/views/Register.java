package cn.shiguopeng.views;

import cn.shiguopeng.Main;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Register extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("注册窗口");
        stage.setWidth(600);
        stage.setHeight(400);

        // stage.getIcons().add(new Image("file:/src/resources/icon.png"));

        // 用户名和密码
        Label usernameLabel = new Label("账号");
        Label passwordLabel = new Label("密码");
        Label confirmPasswordLabel = new Label("请重复密码");
        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        PasswordField confirmPasswordInput = new PasswordField();
        Label loginLabel = new Label("已有账号,去登录");
        Button registerBtn = new Button("注册");

        GridPane gridpane = new GridPane();
        gridpane.add(usernameLabel, 0 ,0);
        gridpane.add(usernameInput, 1 ,0);
        gridpane.add(passwordLabel, 0 ,1);
        gridpane.add(passwordInput, 1 ,1);
        gridpane.add(confirmPasswordLabel, 0 ,2);
        gridpane.add(confirmPasswordInput, 1 ,2);
        gridpane.add(loginLabel, 0 ,3);
        gridpane.add(registerBtn, 1 ,3);

        gridpane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        gridpane.setHgap(20);
        gridpane.setVgap(20);

        Scene scene = new Scene(gridpane);
        stage.setScene(scene);

        loginLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // 跳转去登录界面
                try {
                    new Login().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 关闭当前窗口
                stage.close();
            }
        });
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        stage.show();
    }
}
