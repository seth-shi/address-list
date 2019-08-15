package cn.shiguopeng.databases.cells;

import cn.shiguopeng.databases.tables.ContactTable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class UpdateButtonCell<S, T> extends TableCell<S, T> {

    public void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);
        this.setText(null);
        this.setGraphic(null);

        if (! empty) {

            Button button = new Button("修改");
            button.getStyleClass().addAll("btn-sm", "btn-primary");
            this.setGraphic(button);
            button.setOnMouseClicked((me) -> {

                ContactTable t = (ContactTable) this.getTableView().getItems().get(this.getIndex());
                System.out.println("删除 " + this.getIndex());
            });
        }
    }
}
