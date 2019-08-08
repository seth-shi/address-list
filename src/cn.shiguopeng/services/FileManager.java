package cn.shiguopeng.services;

import cn.shiguopeng.Main;

import java.io.*;

public class FileManager {

    private String rootPath;

    private BufferedWriter usersDataWriter;
    private BufferedReader usersDataReader;


    public FileManager() {

        rootPath = Main.class.getResource("/").getPath();
        if (rootPath.endsWith("/")) {

            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }

        this.rootPath = rootPath;
        this.createDataPathAtEmpty();
    }

    public void createDataPathAtEmpty() {

        File file = new File(this.rootPath.concat("/data"));
        if (!file.exists()) {

            file.mkdir();
        }
    }

    public BufferedWriter getUsersWriter() {

        if (usersDataWriter == null) {

            File file = new File(this.getUsersDataPath());

            try {

                usersDataWriter = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(file, true)
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return usersDataWriter;
    }

    public BufferedReader getUsersReader() {

        if (this.usersDataReader == null) {

            File file = new File(this.getUsersDataPath());
            if (!file.exists()) {

                try {

                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {

                this.usersDataReader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file)
                        )
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return this.usersDataReader;
    }

    private String getUsersDataPath() {

        return this.rootPath.concat("/data/users.dat");
    }
}
