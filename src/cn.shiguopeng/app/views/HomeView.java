package cn.shiguopeng.app.views;

import cn.shiguopeng.app.controllers.HomeController;
import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.databases.cells.ActionsButtonCell;
import cn.shiguopeng.databases.tables.ContactTable;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeView extends ViewFactory {

    private HomeController homeController;

    @Override
    public void render() {

        super.render();

        homeController = (HomeController) this.controller;

        // 最外层容器
        BorderPane container = new BorderPane();

        // 获取数据总条数
        ContactModel model = new ContactModel();
        int count = model.count();
        int limit = model.getPageLimit();
        int pageCount = (int) Math.ceil((double) count / limit);


        Pagination pagination = new Pagination();
        pagination.setPageFactory(this::makePageFactory);
        pagination.setPageCount(pageCount);

        // 设置分页数据
        homeController.setPagination(pagination);
        Panel panel = new Panel();
        HBox header = new HBox();

        Button reloadBtn = new Button("刷新数据");
        reloadBtn.getStyleClass().addAll("btn", "btn-default");
        reloadBtn.setAlignment(Pos.CENTER_RIGHT);
        reloadBtn.setPadding(new Insets(0, 0, 0, 100));

        header.getChildren().addAll(new Text("欢迎使用通讯录"), reloadBtn);
        panel.setHeading(header);
        panel.getStyleClass().add("panel-info");
        panel.setBody(pagination);

        container.setTop(makeMenus(homeController));
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

        // 表格布局
        System.out.println("请求数据 第" + page + " 页");

        ContactModel contactModel = new ContactModel();
        ArrayList<Model> models = contactModel.get(page + 1);

        // 把数据模型转换为table
        ArrayList<ContactTable> contractTables = new ArrayList<>(models.size());
        for (int i = 0; i < models.size(); ++ i) {

            ContactTable c = ((ContactModel)models.get(i)).toTable();
            contractTables.add(i, c);
        }

        // 使用数据表格
        ObservableList<ContactTable> lists = FXCollections.observableArrayList(contractTables);
        TableView tableView = new TableView();

        String[] keys = {
                "no", "name", "sex", "phone", "age", "email"
        };
        String[] values = {
                "编号", "名字", "性别", "手机号", "年龄", "邮箱"
        };

        for (int i = 0; i < keys.length; ++ i) {

            TableColumn c = new TableColumn(values[i]);

            if (i != 0) {
                c.setCellFactory(TextFieldTableCell.forTableColumn());
            }
            c.setCellValueFactory(new PropertyValueFactory<ContactTable, String>(keys[i]));
            // 设置编辑, 第一行不可以编辑
            c.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent cellEditEvent) {

                    String newValue = (String) cellEditEvent.getNewValue();
                    int column = cellEditEvent.getTablePosition().getColumn();
                    ContactTable table = (ContactTable)cellEditEvent.getRowValue();

                    // 编辑拿到旧的数据,替换值,然后更新文本
                    switch (column) {
                        case 1:
                            table.setName(newValue);
                            break;
                        case 2:
                            table.setSex(newValue);
                            break;
                        case 3:
                            table.setPhone(newValue);
                            break;
                        case 4:
                            table.setAge(newValue);
                            break;
                        case 5:
                            table.setEmail(newValue);
                            break;
                    }

                    // 去修改数据文件

                }
            });

            tableView.getColumns().add(c);
        }


        TableColumn actionsColumn = new TableColumn("操作");
        tableView.getColumns().add(actionsColumn);
        actionsColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn tableColumn) {

                return new ActionsButtonCell();
            }
        });

        tableView.setEditable(true);
        // 设置数据
        tableView.setItems(lists);
        // 没有数据的时候显示
        tableView.setPlaceholder(new Text("已经没有更多数据显示了"));
        // 设置自动拉满
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return tableView;
    }
}
