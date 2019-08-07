package cn.shiguopeng;

import cn.shiguopeng.modoules.Encrypt;
import cn.shiguopeng.views.Login;
import javafx.application.Application;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws Exception {

        String encrypt = new Encrypt().encrypt("asdfasdf");

        System.out.println("加密后的字符串=" + encrypt);

        String decrypt = new Encrypt().decrypt(encrypt);
        System.out.println("解密后的字符串=" + decrypt);
        // Application.launch(Login.class, args);
    }
}
