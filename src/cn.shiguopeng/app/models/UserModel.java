package cn.shiguopeng.app.models;

import cn.shiguopeng.Application;
import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.ModelInterface;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.services.Encrypt;

import java.util.Optional;

public class UserModel implements ModelInterface {

    private String username;
    private String password;
    private int dataSize;
    private long dataOffset;

    public UserModel() {

    }

    public UserModel(String username, String password, long dataOffset) {

        this.username = username;
        this.password = password;
        this.dataOffset = dataOffset;

        this.dataSize = username.length() + StoreOptionEnum.SEPARATOR.length() + password.length() + System.lineSeparator().length();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {

        return ((Encrypt) Application.makeObject(Encrypt.class)).decrypt(password);
    }

    public void setPassword(String password) {

        this.password = ((Encrypt) Application.makeObject(Encrypt.class)).encrypt(password);
    }

    public boolean is(String username, String password) {

        password = ((Encrypt) Application.makeObject(Encrypt.class)).encrypt(password);

        return this.username.equals(username) && this.password.equals(password);
    }

    public int getDataSize() {
        return dataSize;
    }

    public long getDataOffset() {
        return dataOffset;
    }

    @Override
    public String getDataFile() {

        return Main.class.getResource("/").getPath() + "/data/users.dat";
    }

    @Override
    public String toDataString() {
        return username + StoreOptionEnum.SEPARATOR + password;
    }
}
