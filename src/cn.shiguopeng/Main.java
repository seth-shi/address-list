package cn.shiguopeng;

import cn.shiguopeng.app.controllers.LoginController;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.foundtions.DatabaseFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {


        DatabaseFactory db = new DatabaseFactory();

        // System.out.println(new UserModel().getDataFile());
        UserModel userModel = new UserModel("david", "7a5320212817086a63");

        db.create(userModel);
        // db.delete(userModel);

//        RandomAccessFile pf = new RandomAccessFile(Main.class.getResource("/").getPath() + "file.dat", "rw");
//        pf.seek(5);
//
//        // 查看换行符占用几个字节
//        byte[] bytes = System.lineSeparator().getBytes();
//
//        pf.write(bytes);

        // System.out.println(System.lineSeparator().length());

        // RandomAccessFile 随机读取文件位置,用以修改

//        javafx.application.Application.launch(LoginController.class, args);
    }
}
