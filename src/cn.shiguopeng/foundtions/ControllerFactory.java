package cn.shiguopeng.foundtions;

import cn.shiguopeng.contracts.Controller;
import cn.shiguopeng.contracts.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class ControllerFactory extends Application implements Controller {

    protected Stage stage;


    @Override
    public void renderView(Stage primaryStage) {

        stage = primaryStage;

        View view = makeView();
        view.setState(primaryStage);
        view.setController(this);
        view.render();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    public View makeView() {

        return null;
    }

}
