package cn.shiguopeng.databases.tables;

import javafx.beans.property.SimpleStringProperty;

public class ContactTable {

    private SimpleStringProperty index;
    private SimpleStringProperty no;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty age;
    private SimpleStringProperty sex;
    private SimpleStringProperty email;

    public ContactTable() {

        this.index = new SimpleStringProperty();
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

    public String getIndex() {
        return index.get();
    }

    public SimpleStringProperty indexProperty() {
        return index;
    }

    public void setIndex(String index) {
        this.index.set(index);
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
}
