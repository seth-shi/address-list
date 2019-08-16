package cn.shiguopeng.databases.cells;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.databases.tables.ContactTable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ActionsButtonCell<S, T> extends TableCell<S, T> {

    public void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);
        this.setText(null);
        this.setGraphic(null);

        if (! empty) {


            Button updateBtn = new Button("修改");
            updateBtn.getStyleClass().addAll("btn-sm", "btn-primary");

            Button delBtn = new Button("删除");
            delBtn.getStyleClass().addAll("btn-sm", "btn-danger");

            HBox b = new HBox();
            b.getChildren().addAll(updateBtn, delBtn);
            this.setGraphic(b);

            updateBtn.setOnMouseClicked((me) -> {

                ContactTable t = (ContactTable) this.getTableView().getItems().get(this.getIndex());

                ContactModel oldModel = new ContactModel(t.getNo());
                ContactModel newModel = new ContactModel(
                        t.getNo(), t.getName(), t.getPhone(), t.getSex(), t.getAge(), t.getEmail()
                );

                if (oldModel.update(newModel))  {

                    // this.getTableView().getItems().remove(this.getIndex());
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("请稍后再试");
                    alert.show();
                }
            });

            // 删除按钮
            delBtn.setOnMouseClicked((me) -> {

                ContactTable t = (ContactTable) this.getTableView().getItems().get(this.getIndex());
                if (new ContactModel(t.getNo()).delete()) {

                    this.getTableView().getItems().remove(this.getIndex());
                    this.getTableView().refresh();
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("请稍后再试");
                    alert.show();
                }
            });
        }
    }
}
