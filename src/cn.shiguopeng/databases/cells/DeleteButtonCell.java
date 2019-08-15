package cn.shiguopeng.databases.cells;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.databases.tables.ContactTable;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class DeleteButtonCell<S, T> extends TableCell<S, T> {

    public void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);
        this.setText(null);
        this.setGraphic(null);

        if (! empty) {

            Button button = new Button("删除");
            button.getStyleClass().addAll("btn-sm", "btn-danger");
            this.setGraphic(button);
            button.setOnMouseClicked((me) -> {

                ContactTable t = (ContactTable) this.getTableView().getItems().get(this.getIndex());

                if (new ContactModel(t.getNo()).delete()) {

                    this.getTableView().getItems().remove(this.getIndex());
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.show();
                }
            });
        }
    }
}
