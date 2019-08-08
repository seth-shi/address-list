package cn.shiguopeng.app.views;

import cn.shiguopeng.Foundtions.ControllerFactory;
import cn.shiguopeng.Foundtions.ViewFactory;
import cn.shiguopeng.app.controllers.LoginController;
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

public class LoginView extends ViewFactory {

    protected LoginController controller;

    public LoginView(Stage stage, ControllerFactory controllerFactory) {

        super(stage, controllerFactory);
    }


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
        scene.getStylesheets().add("/css/bootstrapfx.css");
        stage.setScene(scene);

        // 点击注册
        registerLabel.setOnMouseClicked(controller.gotoRegisterEvent());
        loginBtn.setOnAction(controller.loginEvent(usernameInput, passwordInput));

        System.out.println("show");
        stage.show();
    }
}
