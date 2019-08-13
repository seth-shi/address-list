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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.lang.reflect.Array;
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

        ContactModel contactModel = new ContactModel();
        ArrayList<Model> models = contactModel.get(page + 1);
        // 表格布局

        GridPane gridpane = new GridPane();


        String[] fieldText = new String[] {"序号", "编号", "名字", "性别", "手机号", "年龄", "邮箱", "操作"};
        String[] fieldIds = new String[] {"number", "no", "name", "sex", "phone", "age", "email", "actions"};


        Text headerNumberTxt = new Text("序号");

        // 设置文字大小
        for (int i = 0; i < fieldText.length; ++ i) {

            Text text = new Text(fieldText[i]);
            text.getStyleClass().addAll("h4");
            gridpane.add(text, i, 0);
        }

        for (int i = 0; i < models.size(); ++i) {

            ContactModel model = (ContactModel) models.get(i);
            HashMap<String, Field> fields = model.getFields();

            for (int j = 0; j < fieldIds.length; ++ j) {

                String column = fieldIds[j];

                if (column.equals("number")) {

                    Text numTxt = new Text(String.valueOf(i + 1));
                    gridpane.add(numTxt, j, i + 1);
                    continue;
                } else if (column.equals("actions")) {

                    Button updateBtn = new Button("修改");
                    Button deleteBtn = new Button("删除");
                    updateBtn.getStyleClass().addAll("btn-sm", "btn-primary");
                    deleteBtn.getStyleClass().addAll("btn-sm", "btn-danger");
                    updateBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                            System.out.println("按钮点击的编号行是=" + fields.get("no").getValue());
                        }
                    });

                    gridpane.add(updateBtn, j, i+1);
                    gridpane.add(deleteBtn, j + 1, i+1);
                    continue;
                }

                Text text = new Text(fields.get(fieldIds[j]).getValue());
                text.getStyleClass().addAll("h5", "text-success");
                gridpane.add(text, j, i + 1);
            }
        }

        gridpane.setVgap(9);
        gridpane.setHgap(20);

        return gridpane;
    }
}
