package cn.shiguopeng;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.app.views.HomeView;
import cn.shiguopeng.app.views.LoginView;
import cn.shiguopeng.databases.drives.FileDrive;
import cn.shiguopeng.foundtions.ModelFactory;
import javafx.application.Application;

public class Main {


    public static void main(String[] args) throws Exception {

        // 初始化使用文件驱动装载数据
        ModelFactory.setDrive(new FileDrive());

        Application.launch(LoginView.class, args);
    }
}
