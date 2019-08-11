package cn.shiguopeng.contracts;

import javafx.stage.Stage;

public interface Controller {

    void renderView(Stage stage);
    View makeView();
}
