package cn.shiguopeng.app.models;

import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.databases.tables.ContactTable;
import cn.shiguopeng.enums.StoreOptionEnum;
import cn.shiguopeng.foundtions.ModelFactory;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

public class ContactModel extends ModelFactory {

    // 标识是否登录
    private static UserModel userModel;

    public ContactModel() {

        // 内部维护一个编号查找
        this.indexFields = new String[]{"no", "name", "phone", "sex", "age", "email"};

        this.fields.put("no", new Field(StoreOptionEnum.CHAR_SIZE * 16));
        this.fields.put("name", new Field(StoreOptionEnum.CHAR_SIZE * 12));
        this.fields.put("phone", new Field(StoreOptionEnum.CHAR_SIZE * 12));
        this.fields.put("sex", new Field(StoreOptionEnum.CHAR_SIZE));
        this.fields.put("age", new Field(StoreOptionEnum.CHAR_SIZE * 4));
        this.fields.put("email", new Field(StoreOptionEnum.CHAR_SIZE * 30));
    }

    public ContactModel(String no) {

        this();

        this.fields.get("no").setValue(no);
    }

    public ContactModel(String name, String phone, String sex, String age, String email) {

        this();
        fields.get("no").setValue(createNo());
        fields.get("name").setValue(name);
        fields.get("phone").setValue(phone);
        fields.get("sex").setValue(sex);
        fields.get("age").setValue(age);
        fields.get("email").setValue(email);
    }

    public ContactModel(String no, String name, String phone, String sex, String age, String email) {

        this();
        fields.get("no").setValue(no);
        fields.get("name").setValue(name);
        fields.get("phone").setValue(phone);
        fields.get("sex").setValue(sex);
        fields.get("age").setValue(age);
        fields.get("email").setValue(email);
    }


    public ContactTable toTable() {

        ContactTable t = new ContactTable();
        t.setNo(fields.get("no").getValue());
        t.setName(fields.get("name").getValue());
        t.setPhone(fields.get("phone").getValue());
        t.setSex(fields.get("sex").getValue());
        t.setAge(fields.get("age").getValue());
        t.setEmail(fields.get("email").getValue());

        return t;
    }

    @Override
    public boolean whereIs(Model model) {

        Field field = model.getFields().get("no");

        if (field == null) {

            return false;
        }

        return field.getValue().equals(fields.get("no").getValue());
    }


    @Override
    public Model newInstance() {
        return new ContactModel();
    }

    @Override
    public String getDataFile() {

        if (ContactModel.userModel == null) {

            Logger.getGlobal().info("未登录异常读取");
            System.exit(0);
        }

        String name = ContactModel.userModel.getName();
        String file = new String(Base64.getEncoder().encode(name.getBytes())) + ".dat";

        return Main.class.getResource("/").getPath() + "data/" + file;
    }

    private String createNo() {

        String timeString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String randomString = String.valueOf((int)((Math.random() * 8 + 1) * 1000));

        return timeString + randomString;
    }

    public static void setUserModel(UserModel userModel) {
        ContactModel.userModel = userModel;
    }
}
