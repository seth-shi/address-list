package cn.shiguopeng.services;

import cn.shiguopeng.Application;
import cn.shiguopeng.enums.StoreOptionEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class UsersManager {

    private FileManager fileManager;
    private HashMap<String, String> users = new HashMap<>();


    public UsersManager(FileManager fileManager) {

        this.fileManager = fileManager;
        initUsersData();
    }

    private void initUsersData() {

        BufferedReader reader = fileManager.getUsersReader();
        String str = "";

        try {

            Logger.getGlobal().info("开始读取用户资料库");
            while ((str = reader.readLine()) != null) {

                if (str.trim().isEmpty()) {
                    continue;
                }

                String[] data = str.split(StoreOptionEnum.SEPARATOR);
                users.put(data[0], data[1]);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
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

    public void put(String username, String password) {

        // 把数据持久化
        try {

            // 密码进行加密一下
            password = ((Encrypt) Application.makeObject(Encrypt.class)).encrypt(password);

            fileManager.getUsersWriter().newLine();
            fileManager.getUsersWriter().write(username + StoreOptionEnum.SEPARATOR + password);
            fileManager.getUsersWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        users.put(username, password);
    }
}
