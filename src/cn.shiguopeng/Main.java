package cn.shiguopeng;

import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.databases.drives.FileDrive;
import cn.shiguopeng.foundtions.ModelFactory;
import javafx.application.Application;

import javax.annotation.processing.SupportedSourceVersion;

public class Main {


    public static void main(String[] args) throws Exception {

        // 初始化使用文件驱动装载数据
        ModelFactory.setDrive(new FileDrive());

//         Application.launch(LoginController.class, args);

    }
}
