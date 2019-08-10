package cn.shiguopeng.contracts;

public interface ModelInterface {

    long getDataOffset();
    int getDataSize();
    String getDataFile();
    String toDataString();
}
