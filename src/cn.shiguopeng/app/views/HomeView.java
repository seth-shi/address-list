package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.HomeController;
import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeView extends ViewFactory {

    @Override
    public void render() {

        super.render();

        HomeController controller = (HomeController) this.controller;

        // 最外层容器
        BorderPane container = new BorderPane();

        Panel panel = new Panel("欢迎使用通讯录");
        panel.getStyleClass().add("panel-info");

        container.setTop(makeMenus(controller));

        // 获取数据总条数
        ContactModel model = new ContactModel();
        int count = model.count();
        int limit = model.getPageLimit();
        int pageCount = (int) Math.ceil((double) count / limit);


        Pagination pagination = new Pagination();
        pagination.setPageFactory(this::makePageFactory);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageCount(pageCount);

        panel.setBody(pagination);
        container.setCenter(panel);

        Scene scene = new Scene(container, 100, 20);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);



        stage.show();
    }


    private Node makeMenus(HomeController controller) {

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());


        // 退出菜单
        Menu fileMenu = new Menu("文件");
        MenuItem exitItem = new MenuItem("退出");
        exitItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(exitItem, new SeparatorMenuItem());

        // 新建联系人
        Menu addMenu = new Menu("添加联系人");
        addMenu.setOnAction(controller.addContactAction());

        menuBar.getMenus().addAll(fileMenu, addMenu);

        return menuBar;
    }

    private Node makePageFactory(Integer page) {

        ArrayList<Model> models = new ContactModel().get(page + 1);
        // 表格布局

        GridPane gridpane = new GridPane();

        Text headerNumberTxt = new Text("序号");
        Text headerNoTxt = new Text("编号");
        Text headerNameTxt = new Text("名字");
        Text headerPhoneTxt = new Text("手机号");

        gridpane.add(headerNumberTxt, 0, 0);
        gridpane.add(headerNoTxt, 1, 0);
        gridpane.add(headerNameTxt, 2, 0);
        gridpane.add(headerPhoneTxt, 3, 0);

        for (int i = 0; i < models.size(); ++i) {

            ContactModel model = (ContactModel) models.get(i);

            HashMap<String, Field> fields = model.getFields();

            Text numberTxt = new Text(String.valueOf(i + 1));
            Text noTxt = new Text(fields.get("no").getValue());
            Text nameTxt = new Text(fields.get("name").getValue());
            Text phoneTxt = new Text(fields.get("phone").getValue());

            gridpane.add(numberTxt, 0, i + 1);
            gridpane.add(noTxt, 1, i + 1);
            gridpane.add(nameTxt, 2, i + 1);
            gridpane.add(phoneTxt, 3, i + 1);
        }


        // 如果不够，那也进行填充


        gridpane.setVgap(9);
        gridpane.setHgap(10);
        gridpane.setAlignment(Pos.CENTER);

        return gridpane;
    }
}
