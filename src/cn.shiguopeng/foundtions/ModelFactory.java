package cn.shiguopeng.foundtions;

import cn.shiguopeng.contracts.DataDrive;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;

import java.util.HashMap;

public class ModelFactory implements Model {

    protected String[] indexFields = new String[]{};
    protected HashMap<String, Field> fields = new HashMap<>();

    private static DataDrive drive;

    public static void setDrive(DataDrive drive) {

        ModelFactory.drive = drive;
    }


    @Override
    public boolean create() {

        return drive.create(this);
    }

    @Override
    public boolean delete() {

        return drive.delete(this);
    }

    @Override
    public Model first() {

        return drive.first(this);
    }


    @Override
    public boolean update(Model newModel) {

        return drive.update(this, newModel);
    }

    @Override
    public boolean whereIs(Model model) {
        return false;
    }

    @Override
    public boolean is(Model model) {

        for (String key : indexFields) {

            Field field = model.getFields().get(key);
            if (field == null) {

                return false;
            }

            if (! fields.get(key).getValue().equals(field.getValue())) {

                return false;
            }
        }

        return true;
    }

    @Override
    public HashMap<String, Field> getFields() {

        return fields;
    }

    @Override
    public String[] getIndexFields() {

        return indexFields;
    }

    @Override
    public Model newInstance() {

        return new ModelFactory();
    }

    @Override
    public void setField(String key, Field val) {

        for (String field : indexFields) {

            if (field.equals(key)) {

                fields.put(key, val);
            }
        }
    }

    @Override
    public String getDataFile() {
        return null;
    }

    @Override
    public int getDataSize() {

        int size = 0;

        for (String key : indexFields) {

            size += fields.get(key).getSize();
        }

        return size;
    }

    @Override
    public String toString() {
        return null;
    }
}
