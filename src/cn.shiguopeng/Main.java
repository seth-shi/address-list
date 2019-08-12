package cn.shiguopeng;

import cn.shiguopeng.app.controllers.HomeController;
import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.databases.drives.FileDrive;
import cn.shiguopeng.foundtions.ModelFactory;
import javafx.application.Application;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.logging.SimpleFormatter;

public class Main {


    public static void main(String[] args) throws Exception {

        // 初始化使用文件驱动装载数据
        ModelFactory.setDrive(new FileDrive());


        UserModel userModel = (UserModel) new UserModel("david").first();

        ContactModel.setUserModel(userModel);

        Application.launch(HomeController.class, args);
    }
}
