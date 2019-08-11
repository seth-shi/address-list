package cn.shiguopeng.app.models;

import cn.shiguopeng.Main;
import cn.shiguopeng.base.Field;
import cn.shiguopeng.enums.StoreOptionEnum;

public class UserModel {

    private Field username = new Field(StoreOptionEnum.BYTE_SIZE * 8);
    private Field password = new Field(StoreOptionEnum.BYTE_SIZE * 32);

    public UserModel() {

    }

    public UserModel(String username, String password) {

        this.username.setValue(username);
        this.password.setValue(password);
    }

    public Field getUsername() {
        return username;
    }

    public Field getPassword() {
        return password;
    }

    public String getDataFile() {

        return Main.class.getResource("/").getPath() + "/data/users.dat";
    }

    public String toDataString() {

        return username.toString() + password.toString();
    }
}
