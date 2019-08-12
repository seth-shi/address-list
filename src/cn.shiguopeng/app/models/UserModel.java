package cn.shiguopeng.app.models;

import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.foundtions.ModelFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserModel extends ModelFactory {

    public UserModel() {

        this.indexFields = new String[] {"username", "password"};

        fields.put("username", new Field(StoreOptionEnum.CHAR_SIZE * 12));
        fields.put("password", new Field(StoreOptionEnum.CHAR_SIZE * 32));
    }

    public UserModel(String username) {

        this();
        fields.get("username").setValue(username);
    }


    public UserModel(String username, String password) {

        this();

        fields.get("username").setValue(username);

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(password.getBytes());
            //获得加密后的数据
            password = new BigInteger(1, md.digest()).toString(16);

            int passwordSize = this.fields.get("password").getSize();
            if (password.length() < passwordSize) {

                password = "0".repeat(password.length() - passwordSize) + password;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        fields.get("password").setValue(password);
    }

    @Override
    public Model newInstance() {

        return new UserModel();
    }


    @Override
    public boolean whereIs(Model model) {

        Field field = model.getFields().get("username");

        if (field == null) {

            return false;
        }

        return field.getValue().equals(fields.get("username").getValue());
    }



    public String getDataFile() {

        return Main.class.getResource("/").getPath() + "/data/users.dat";
    }

    public String getName() {

        return fields.get("username").getValue();
    }
}
