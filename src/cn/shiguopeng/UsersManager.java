package cn.shiguopeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class UsersManager {

    private static UsersManager instance;
    private FileManager fileManager;
    private HashMap<String, String> users = new HashMap<>();

    public static UsersManager getInstance() {

        if (UsersManager.instance == null) {

            UsersManager.instance = new UsersManager(FileManager.getInstance());
        }

        return UsersManager.instance;
    }

    public boolean has(String phone) {

        if (users.isEmpty()) {

            return false;
        }

        return users.containsKey(phone);
    }

    public String get(String phone) {

        if (users.isEmpty()) {

            return "";
        }

        return users.get(phone);
    }

    /**
     * 初始化读取所有用户数据进入系统
     */
    private UsersManager(FileManager fileManager) {

        this.fileManager = fileManager;
        initUsersData();
    }

    private void initUsersData() {

        BufferedReader reader = fileManager.getUsersReader();
        String str = "";

        try {
            while ((str = reader.readLine()) != null) {

                String[] data = str.split("=");
                users.put(data[0], data[1]);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
