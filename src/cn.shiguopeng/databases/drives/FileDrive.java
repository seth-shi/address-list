package cn.shiguopeng.databases.drives;

import cn.shiguopeng.contracts.DataDrive;
import cn.shiguopeng.contracts.Model;
import cn.shiguopeng.databases.Field;

import java.io.*;
import java.nio.CharBuffer;
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
    public void create(Model model) {

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
    }

    @Override
    public void delete(Model model) {

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
    }

    @Override
    public void update(Model model) {

    }

    public Model first(Model model) {

        // 先获取文件
        File pf = fileNotExistsCreate(model.getDataFile());

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(pf)
            );

            var charBuffer = new char[model.getDataSize()];


            // 一次读入一行数据
            Model newModel = model.newInstance();
            // 新模型的新属性列表
            String[] indexFields = newModel.getIndexFields();
            HashMap<String, Field> fields = newModel.getFields();

            while ((reader.read(charBuffer)) != -1) {

                String line = new String(charBuffer);


                int offset = 0;
                for (String key : indexFields) {

                    Field field = fields.get(key);

                    int size = field.getSize();
                    field.setValue(line.substring(offset, size));
                    offset += size;

                    newModel.setField(key, field);
                }


                if (model.whereIs(newModel)) {

                    reader.close();;
                    return newModel;
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
