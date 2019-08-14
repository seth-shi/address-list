package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.HomeController;
import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.databases.tables.ContractTable;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeView extends ViewFactory {

    private HomeController homeController;

    @Override
    public void render() {

        super.render();

        homeController = (HomeController) this.controller;

        // 最外层容器
        BorderPane container = new BorderPane();

        Panel panel = new Panel("欢迎使用通讯录");
        panel.getStyleClass().add("panel-info");

        container.setTop(makeMenus(homeController));

        // 获取数据总条数
        ContactModel model = new ContactModel();
        int count = model.count();
        int limit = model.getPageLimit();
        int pageCount = (int) Math.ceil((double) count / limit);


        Pagination pagination = new Pagination();
        pagination.setPageFactory(this::makePageFactory);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageCount(pageCount);

        // 设置分页数据
        homeController.setPagination(pagination);

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
        System.out.println("请求数据 第" + page + " 页");

        GridPane gridpane = new GridPane();


        String[] fieldText = new String[] {"序号", "编号", "名字", "性别", "手机号", "年龄", "邮箱", "操作"};
        String[] fieldIds = new String[] {"number", "no", "name", "sex", "phone", "age", "email", "actions"};


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


                    updateBtn.setOnAction(homeController.updateContact(fields.get("no").getValue()));
                    deleteBtn.setOnAction(homeController.deleteContact(fields.get("no").getValue()));

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



        ArrayList<ContractTable> contractTables = new ArrayList<>(models.size());
        for (int i = 0; i < models.size(); ++ i) {

            HashMap<String, Field> fields = models.get(i).getFields();

            ContractTable c = new ContractTable();
            c.setIndex(String.valueOf(i + 1));
            c.setNo(fields.get("no").getValue());
            c.setName(fields.get("name").getValue());
            c.setPhone(fields.get("phone").getValue());
            c.setSex(fields.get("sex").getValue());
            c.setAge(fields.get("age").getValue());
            c.setEmail(fields.get("email").getValue());
            contractTables.add(i, c);
        }

        System.out.println(Arrays.toString(contractTables.toArray()));

        // 使用数据表格
        ObservableList<ContractTable> lists = FXCollections.observableArrayList(contractTables);
        TableView tableView = new TableView();

        TableColumn indexColumn = new TableColumn("序号");
        TableColumn noColumn = new TableColumn("编号");
        TableColumn nameColumn = new TableColumn("名字");
        TableColumn sexColumn = new TableColumn("性别");
        TableColumn phoneColumn = new TableColumn("手机号");
        TableColumn ageColumn = new TableColumn("年龄");
        TableColumn emailColumn = new TableColumn("邮箱");
        TableColumn actionsColumn = new TableColumn("操作");
        TableColumn updateColumn = new TableColumn("修改");
        TableColumn deleteColumn = new TableColumn("删除");
        actionsColumn.getColumns().addAll(updateColumn, deleteColumn);

        // 添加列 隐藏编号
        noColumn.setVisible(false);
        tableView.getColumns().addAll(indexColumn, noColumn, nameColumn, sexColumn, phoneColumn, ageColumn, emailColumn, actionsColumn);


        // 绑定数据列
        indexColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("index"));
        noColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("no"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("sex"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("phone"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("age"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("email"));
//        actionsColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("sex"));
//        updateColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("sex"));
//        deleteColumn.setCellValueFactory(new PropertyValueFactory<ContactModel, String>("sex"));

        tableView.setItems(lists);
        // 没有数据的时候显示
        tableView.setPlaceholder(new Text("已经没有更多数据显示了"));
        // 设置自动拉满
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return tableView;
    }
}
