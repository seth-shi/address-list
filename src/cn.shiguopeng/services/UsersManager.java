package cn.shiguopeng.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class UsersManager {

    private FileManager fileManager;
    private HashMap<String, String> users = new HashMap<>();




    public UsersManager(int str) {

        System.out.println("参数构造" + str);
    }

    public UsersManager() {

        System.out.println("构造出来");
    }

    public boolean has(String username) {

        if (users.isEmpty()) {

            return false;
        }

        return users.containsKey(username);
    }

    public String get(String username) {

        if (users.isEmpty()) {

            return "";
        }

        return users.get(username);
    }

    public String put(String username, String password) {

        // 把数据持久化
        try {
            fileManager.getUsersWriter().newLine();
            fileManager.getUsersWriter().write(username + "=" + password);
            fileManager.getUsersWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users.put(username, password);
    }

    private void initUsersData() {

        BufferedReader reader = fileManager.getUsersReader();
        String str = "";

        try {

            System.out.println("初始化数据");
            while ((str = reader.readLine()) != null) {

                String[] data = str.split("=");
                users.put(data[0], data[1]);
            }

            System.out.println(str);


        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
