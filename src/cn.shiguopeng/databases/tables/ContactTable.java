package cn.shiguopeng.databases.tables;

import cn.shiguopeng.app.models.ContactModel;
import cn.shiguopeng.databases.Field;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;

public class ContactTable {

    private SimpleStringProperty no;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty age;
    private SimpleStringProperty sex;
    private SimpleStringProperty email;

    public ContactTable() {

        this.no = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.age = new SimpleStringProperty();
        this.sex = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public String getNo() {
        return no.get();
    }

    public SimpleStringProperty noProperty() {
        return no;
    }


    public void setNo(String no) {
        this.no.set(no);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ContactModel toModel() {

        ContactModel model = new ContactModel();
        HashMap<String, Field> fields = model.getFields();
        fields.get("no").setValue(this.getNo());
        fields.get("name").setValue(this.getName());
        fields.get("sex").setValue(this.getSex());
        fields.get("phone").setValue(this.getPhone());
        fields.get("age").setValue(this.getAge());
        fields.get("email").setValue(this.getEmail());

        return model;
    }
}
