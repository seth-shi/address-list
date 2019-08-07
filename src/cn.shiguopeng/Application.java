package cn.shiguopeng;

import cn.shiguopeng.services.FileManager;
import cn.shiguopeng.services.UsersManager;

import java.util.HashMap;

public class Application extends Container {

    private static Application ourInstance = new Application();

    public static Application getInstance() {
        return ourInstance;
    }

    private Application() {
    }

    public HashMap<String, Object> getInstances() {

        return instances;
    }

}
