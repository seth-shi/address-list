package cn.shiguopeng;

import cn.shiguopeng.services.Encrypt;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {


        Application application = Application.getInstance();
        Encrypt encrypt = (Encrypt) application.make(Encrypt.class);

        System.out.println("-------------------------");
        System.out.println(encrypt);
        System.out.println(encrypt.encrypt("abcd"));

        System.out.println(Arrays.toString(application.getInstances().values().toArray()));

        // Application.launch(Login.class, args);
    }
}
