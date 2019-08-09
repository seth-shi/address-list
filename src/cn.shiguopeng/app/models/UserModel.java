package cn.shiguopeng.app.models;

import cn.shiguopeng.Application;
import cn.shiguopeng.Foundtions.ModelFactory;
import cn.shiguopeng.services.Encrypt;

public class UserModel extends ModelFactory {

    private String username;
    private String password;

    public UserModel(String username, String password) {

        this.username = username;
        this.password = password;
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


}
