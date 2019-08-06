package cn.shiguopeng;

import java.io.*;

public class FileManager {

    private static FileManager instance;
    private String rootPath;
    private BufferedReader usersDataReader;

    public static FileManager getInstance() {

        if (FileManager.instance == null) {

            FileManager.instance = new FileManager(Main.class.getResource("/").getPath());
        }

        return FileManager.instance;
    }

    public FileManager(String rootPath) {

        if (rootPath.endsWith("/")) {

            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }

        this.rootPath = rootPath;
        this.createDataPathAtEmpty();
    }

    public void createDataPathAtEmpty() {

        File file = new File(this.rootPath.concat("/data"));
        if (! file.exists()) {

            file.mkdir();
        }
    }

    public BufferedReader getUsersReader() {

        if (this.usersDataReader == null) {

            File file = new File(this.getUsersDataPath());
            if (! file.exists()) {

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
