package cn.shiguopeng.contracts;

import cn.shiguopeng.databases.Field;

import java.util.HashMap;

public interface Model {

    void create();
    Model first();


    boolean whereIs(Model model);

    HashMap<String, Field> getFields();
    String[] getIndexFields();

    Model newInstance();
    void setField(String key, Field val);

    String getDataFile();
    int getDataSize();
}
