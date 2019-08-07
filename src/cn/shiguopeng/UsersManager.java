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
