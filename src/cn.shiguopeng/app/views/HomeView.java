package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.HomeController;
import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class HomeView extends ViewFactory {

    @Override
    public void render() {

        super.render();

        Panel panel = new Panel("欢迎使用通讯录");
        panel.getStyleClass().add("panel-info");

        Pagination pagination = new Pagination(5);
        pagination.setPageFactory(this::makePageFactory);

        panel.setBody(pagination);

        Scene scene = new Scene(panel, 100, 20);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);


        HomeController controller = (HomeController) this.controller;
//
//        // 点击注册
//        registerLabel.setOnMouseClicked(controller.gotoRegisterEvent());
//        loginBtn.setOnAction(controller.loginEvent(usernameInput, passwordInput));

        stage.show();
    }

    private Node makePageFactory(Integer page) {

        // 表格布局
        System.out.println("page=" + page);
        return new Text("88888888888888");
    }
}
