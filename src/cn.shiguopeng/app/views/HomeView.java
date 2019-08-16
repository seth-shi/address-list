package cn.shiguopeng.app.views;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.cells.ActionsButtonCell;
import cn.shiguopeng.databases.tables.ContactTable;
import cn.shiguopeng.foundtions.ViewFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class HomeView extends ViewFactory {


    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);

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
        Panel panel = new Panel();
        HBox header = new HBox();


        header.getChildren().addAll(new Text("欢迎使用通讯录"));
        panel.setHeading(header);
        panel.getStyleClass().add("panel-info");
        panel.setBody(pagination);

        container.setTop(makeMenus());
        container.setCenter(panel);

        Scene scene = new Scene(container, 100, 20);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        stage.setScene(scene);

        stage.show();
    }


    private Node makeMenus() {

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());


        // 退出菜单
        Menu fileMenu = new Menu("文件");
        MenuItem exitItem = new MenuItem("退出");
        exitItem.setOnAction(actionEvent -> Platform.exit());


        MenuItem addItem = new MenuItem("添加联系人");
        addItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Dialog dialog = new Dialog<>();
                dialog.setTitle("添加联系人");
                dialog.setHeaderText("通讯录");

                ButtonType addButtonType = new ButtonType("添加", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                String[] keys = {
                        "name", "sex", "phone", "age", "email"
                };
                String[] values = {
                        "名字", "性别", "手机号", "年龄", "邮箱"
                };

                String[] defaults = {
                        "", "男", "", "21", ""
                };

                HashMap<String, TextField> texts = new HashMap<>();

                for (int i = 0; i < keys.length; ++ i) {


                    TextField txt = new TextField();
                    txt.setText(defaults[i]);
                    txt.setPromptText(values[i]);

                    texts.put(keys[i], txt);
                    grid.add(new Label(values[i]), 0, i);
                    grid.add(txt, 1, i);
                }

                dialog.getDialogPane().setContent(grid);

                Optional result = dialog.showAndWait();
                // 确定添加
                if (result.get().equals(addButtonType)) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    // 验证名字不能为空
                    String name = texts.get("name").getText();
                    if (name.isEmpty()) {
                        alert.setContentText("姓名不能为空");
                        alert.show();
                        return;
                    }

                    String phone = texts.get("phone").getText();
                    if (phone.length() != 11) {
                        alert.setContentText("请输入正确的手机号");
                        alert.show();
                        return;
                    }

                    String sex =  texts.get("sex").getText();
                    if (! (sex.equals("男") || sex.equals("女"))) {
                        alert.setContentText("性别只有男和女");
                        alert.show();
                        return;
                    }

                    String age = texts.get("age").getText();
                    if (Integer.valueOf(age) < 0) {
                        alert.setContentText("请输入正确的年龄");
                        alert.show();
                        return;
                    }
                    ContactModel model = new ContactModel(
                            name,
                            phone,
                            sex,
                            age,
                            texts.get("email").getText()
                    );

                    if (model.create()) {

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("添加联系人成功");
                        alert.show();
                    }

                }

            }
        });

        fileMenu.getItems().addAll(addItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().addAll(fileMenu);

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
                    ContactModel model = table.toModel();
                    ContactModel oldModel = new ContactModel(table.getNo());
                    if (! oldModel.update(model)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("请稍后再试");
                        alert.show();
                    }
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
