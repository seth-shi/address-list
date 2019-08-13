package cn.shiguopeng.app.controllers;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.app.views.HomeView;
import cn.shiguopeng.app.views.LoginView;
import cn.shiguopeng.contracts.View;
import cn.shiguopeng.foundtions.ControllerFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class HomeController extends ControllerFactory {

    private Pagination pagination;

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public void start(Stage stage) throws Exception {

        renderView(stage);
    }

    public EventHandler<ActionEvent> updateContact(String no) {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("no=" + no);
            }
        };
    }

    public EventHandler<ActionEvent> deleteContact(String no) {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("警告");
                alert.setContentText("确定要删除联系人吗");
                Optional<ButtonType> result =  alert.showAndWait();

                if (! ButtonType.OK.equals(result.get())) {
                    return;
                }

                // 删除联系人
                boolean flag = new ContactModel(no).delete();
                // 重载数据
                if (! flag) {
                    Alert resultAlert = new Alert(Alert.AlertType.ERROR);
                    resultAlert.setContentText("请稍后再试");
                    resultAlert.show();
                    return;
                }

                // 如果现在的总条数不够撑住这一页,那么跳到前一页
                ContactModel model = new ContactModel();
                int count = model.count();
                int currPageIndex = pagination.getCurrentPageIndex();
                int pageCount = (int) Math.ceil((double) count / model.getPageLimit());

                System.out.println("当前页码=" + currPageIndex);
                if (pageCount <= currPageIndex) {
                    // 第一页是从 0 开始
                    currPageIndex = pageCount - 1;
                }

                // 第一页是从 0 开始
                if (currPageIndex <= 0) {
                    currPageIndex = 0;
                }

                // TODO 数据已经变更, 但是渲染没这么多, 覆盖不了
                pagination.setCurrentPageIndex(currPageIndex);
                pagination.setPageCount(pageCount);

            }
        };
    }

    public EventHandler<ActionEvent> addContactAction() {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("点击添加联系人");

                Stage addStage = new Stage(StageStyle.UNIFIED);
                addStage.initOwner(stage);
                addStage.show();
            }
        };
    }

    @Override
    public View makeView() {

        return new HomeView();
    }
}
