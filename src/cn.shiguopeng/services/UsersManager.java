package cn.shiguopeng.services;

import cn.shiguopeng.Application;
import cn.shiguopeng.app.models.UserModel;
import cn.shiguopeng.enums.StoreOptionEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class UsersManager {

    private FileManager fileManager;
    private HashMap<String, UserModel> users = new HashMap<>();


    public UsersManager(FileManager fileManager) {

        this.fileManager = fileManager;
        initUsersData();
    }

    private void initUsersData() {

        BufferedReader reader = fileManager.getUsersReader();
        String line = "";

        try {

            Logger.getGlobal().info("开始读取用户资料库");
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                // 根据大小读取
                UserModel user = new UserModel();
                int usernameSize = user.getUsername().getSize();

                user.getUsername().setValue(line.substring(0, usernameSize));
                user.getPassword().setValue(line.substring(usernameSize));
            }
            reader.close();

            Logger.getGlobal().info("读取用户资料完毕,总共["+ users.size() +"]个用户信息");
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

    public UserModel get(String username) {

        if (users.isEmpty()) {

            return new UserModel();
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

        users.put(username, new UserModel());
    }
}
