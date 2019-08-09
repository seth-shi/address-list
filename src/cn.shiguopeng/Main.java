package cn.shiguopeng;

import cn.shiguopeng.app.controllers.LoginController;

public class Main {


    public static void main(String[] args) throws Exception {

        // RandomAccessFile 随机读取文件位置,用以修改

        javafx.application.Application.launch(LoginController.class, args);
    }
}
