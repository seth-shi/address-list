package cn.shiguopeng.contracts;

import javafx.stage.Stage;

public interface View {

    void setState(Stage stage);
    void setController(Controller controller);
    void render();
}
