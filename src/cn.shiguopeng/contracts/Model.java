package cn.shiguopeng.contracts;

import cn.shiguopeng.databases.Field;

import java.util.HashMap;

public interface Model {

    boolean create();
    boolean delete();
    boolean update(Model model);
    Model first();


    boolean whereIs(Model model);
    boolean is(Model model);

    HashMap<String, Field> getFields();
    String[] getIndexFields();

    Model newInstance();
    void setField(String key, Field val);

    String getDataFile();
    int getDataSize();
}
