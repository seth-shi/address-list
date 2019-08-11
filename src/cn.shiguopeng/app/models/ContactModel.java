package cn.shiguopeng.app.models;

import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.foundtions.ModelFactory;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ContactModel extends ModelFactory {

    // 标识是否登录
    private static UserModel userModel;

    public ContactModel() {

        // 内部维护一个编号查找
        this.indexFields = new String[]{"no", "name", "phone"};

        this.fields.put("no", new Field(StoreOptionEnum.CHAR_SIZE * 16));
        this.fields.put("name", new Field(StoreOptionEnum.CHAR_SIZE * 12));
        this.fields.put("phone", new Field(StoreOptionEnum.CHAR_SIZE * 12));
    }

    @Override
    public boolean whereIs(Model model) {

        Field field = model.getFields().get("username");

        if (field == null) {

            return false;
        }

        return field.getValue().equals(fields.get("no").getValue());
    }


    @Override
    public Model newInstance() {
        return super.newInstance();
    }

    @Override
    public String getDataFile() {

        if (ContactModel.userModel == null) {

            System.out.println("异常读取");
        }

        String name = ContactModel.userModel.getName();
        String file = new String(Base64.getEncoder().encode(name.getBytes())) + ".dat";

        return Main.class.getResource("/").getPath() + file;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private String createNo() {

        String timeString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String randomString = String.valueOf((int)((Math.random() * 8 + 1) * 1000));

        return timeString + randomString;
    }
}
