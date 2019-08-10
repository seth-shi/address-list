package cn.shiguopeng.foundtions;

import cn.shiguopeng.Main;
import cn.shiguopeng.contracts.ModelInterface;

import java.io.*;
import java.util.ArrayList;

public class DatabaseFactory{

    private RandomAccessFile pf;
    private ModelInterface model;


    public boolean create(ModelInterface model) {

        String filePath = model.getDataFile();

        // 创建目录
        File pf = new File(filePath);


        if (! pf.exists()) {

            // 可能文件夹也不存在
            File dir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
            if (! dir.isDirectory()) {
                dir.mkdirs();
            }

            try {


                pf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {

            // 直接往文件末尾写入文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pf, true)));
            bufferedWriter.write(model.toDataString() + System.lineSeparator());
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public boolean delete(ModelInterface model) {

        // 先拿到 offset
        try {

            RandomAccessFile pf = new RandomAccessFile(model.getDataFile(), "rw");

            String line = "";

            long oldOffset = model.getDataOffset();
            long newOffset = model.getDataOffset() + model.getDataOffset();

            pf.seek(newOffset);
            // 把新的文件内容设置到新文件
            while ((line = pf.readLine()) != null) {

                // 先拿到当前偏移量
                newOffset = pf.getFilePointer();

                // 往之前的文件插入新的内容覆盖
                pf.seek(oldOffset);
                pf.write(line.getBytes());

                System.out.println("旧指针=" + oldOffset + "  新指针="+newOffset + "  内容=" + line);
                // 然后再切换到新指针
                oldOffset = newOffset;
                pf.seek(newOffset);
            }

            pf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    
    public boolean update(ModelInterface model) {
        return false;
    }

    
    public ModelInterface first() {
        return null;
    }

    
    public ArrayList get() {
        return null;
    }
}
