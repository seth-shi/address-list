package cn.shiguopeng.databases.drives;

import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.DataDrive;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;
import cn.shiguopeng.foundtions.ModelFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileDrive implements DataDrive {

    private File fileNotExistsCreate(String filePath) {

        File pf = new File(filePath);

        if (! pf.exists()) {

            File df = new File(filePath.substring(0, filePath.lastIndexOf('/')));
            if (! df.isDirectory()) {

                df.mkdirs();
            }

            try {
                pf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pf;
    }

    @Override
    public boolean create(Model model) {

        // 先获取文件
        File pf = fileNotExistsCreate(model.getDataFile());

        try {

            // 直接往文件末尾添加数据
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(pf, true)
            );

            writer.write(model.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean delete(Model model) {

        boolean flag = false;

        // 先获取文件
        File pf = fileNotExistsCreate(model.getDataFile());

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader(pf)
            );
            // 临时文件
            File tmpFile = File.createTempFile("users", ".dat", new File(Main.class.getResource("/").getPath()));
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(tmpFile)
            );


            char[] charBuffer = new char[model.getDataSize()];
            // 一次读入一行数据
            Model readModel = model.newInstance();
            // 新模型的新属性列表
            String[] indexFields = readModel.getIndexFields();
            HashMap<String, Field> fields = readModel.getFields();

            while ((reader.read(charBuffer)) != -1) {

                String line = new String(charBuffer);


                int offset = 0;
                for (String key : indexFields) {

                    Field field = fields.get(key);

                    int size = field.getSize();
                    field.setValue(line.substring(offset, size));
                    offset += size;

                    readModel.setField(key, field);
                }


                if (model.whereIs(readModel)) {

                    flag = true;
                    // 修改文件内容
                    continue;
                }

                writer.write(line);
            }

            writer.close();
            reader.close();

            // 替换临时文件
            pf.delete();
            tmpFile.renameTo(pf);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public boolean update(Model oldModel, Model newModel) {

        // 先获取文件
        File pf = fileNotExistsCreate(oldModel.getDataFile());

        try {

            BufferedReader reader = new BufferedReader(
                    new FileReader(pf)
            );
            // 临时文件
            File tmpFile = File.createTempFile("users", ".dat", new File(Main.class.getResource("/").getPath()));
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(tmpFile)
            );


            char[] charBuffer = new char[oldModel.getDataSize()];
            // 一次读入一行数据
            Model readModel = oldModel.newInstance();
            // 新模型的新属性列表
            String[] indexFields = readModel.getIndexFields();
            HashMap<String, Field> fields = readModel.getFields();

            while ((reader.read(charBuffer)) != -1) {

                String line = new String(charBuffer);


                int offset = 0;
                for (String key : indexFields) {

                    Field field = fields.get(key);

                    int size = field.getSize();
                    field.setValue(line.substring(offset, size));
                    offset += size;

                    readModel.setField(key, field);
                }


                if (oldModel.whereIs(readModel)) {

                    // 修改文件内容
                    line = newModel.toString();
                    System.out.println("内容="+line + " 内容长度="+line.length());
                }

                writer.write(line);
            }

            writer.close();
            reader.close();

            // 替换临时文件
            pf.delete();
            tmpFile.renameTo(pf);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Model first(Model model) {

        // 先获取文件
        File pf = fileNotExistsCreate(model.getDataFile());

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(pf)
            );

            char[] charBuffer = new char[model.getDataSize()];


            // 一次读入一行数据
            Model newModel = model.newInstance();
            // 新模型的新属性列表
            String[] indexFields = newModel.getIndexFields();
            HashMap<String, Field> fields = newModel.getFields();

            while ((reader.read(charBuffer)) != -1) {

                String line = new String(charBuffer);


                System.out.println(line);
                int offset = 0;
                for (String key : indexFields) {

                    Field field = fields.get(key);

                    int size = field.getSize();
                    field.setValue(line.substring(offset, size+offset));
                    offset += size;

                    newModel.setField(key, field);
                }


                if (model.whereIs(newModel)) {

                    reader.close();
                    return newModel;
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Model> get(Model model, int page) {

        // 先获取文件
        int limit = model.getPageLimit();
        File pf = fileNotExistsCreate(model.getDataFile());
        ArrayList<Model> models = new ArrayList<>(limit);

        int breakLines = (page - 1) * limit;

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(pf)
            );

            char[] charBuffer = new char[model.getDataSize()];


            while ((reader.read(charBuffer)) != -1) {

                if (breakLines > 0) {
                    -- breakLines;
                    continue;
                }

                String line = new String(charBuffer);

                // 一次读入一行数据
                Model newModel = model.newInstance();
                // 新模型的新属性列表
                String[] indexFields = newModel.getIndexFields();
                HashMap<String, Field> fields = newModel.getFields();

                int offset = 0;
                for (String key : indexFields) {

                    Field field = fields.get(key);

                    int size = field.getSize();
                    field.setValue(line.substring(offset, size+offset));
                    offset += size;

                    newModel.setField(key, field);
                }
                
                models.add(newModel);
                if (models.size() == limit) {
                    break;
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return models;
    }
}
