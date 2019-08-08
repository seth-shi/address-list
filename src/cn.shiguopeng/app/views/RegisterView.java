package cn.shiguopeng.app.views;

import cn.shiguopeng.Foundtions.ControllerFactory;
import cn.shiguopeng.Foundtions.ViewFactory;
import cn.shiguopeng.app.controllers.RegisterController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class RegisterView extends ViewFactory {

    private RegisterController controller;

    public RegisterView(Stage stage, ControllerFactory controllerFactory) {
        super(stage, controllerFactory);

        this.controller = (RegisterController) controllerFactory;
    }

    @Override
    public void make() {


        stage.getIcons().add(new Image("/resources/icon.png"));
        stage.setTitle("通讯录");
        stage.setWidth(600);
        stage.setHeight(400);

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
        scene.getStylesheets().add("/css/bootstrapfx.css");
        stage.setScene(scene);

        // 点击注册
        loginLabel.setOnMouseClicked(controller.gotoLoginEvent());
        registerButton.setOnAction(controller.registerEvent(usernameInput, passwordInput, confirmPasswordInput));

        stage.show();
    }
}
