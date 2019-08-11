package cn.shiguopeng.databases;

import cn.shiguopeng.enums.StoreOptionEnum;

public class Field {

    private int size;
    private String value;

    public Field(int size) {
        this.size = size;
    }

    public Field(int size, String value) {

        this.size = size;
        this.setValue(value);
    }

    public void setValue(String value) {

        if (value.contains(String.valueOf(StoreOptionEnum.FILL_BLACK_MARK))) {

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < value.length(); ++ i) {

                char c = value.charAt(i);
                if (c != StoreOptionEnum.FILL_BLACK_MARK) {

                    builder.append(c);
                }
            }
            value = builder.toString();
        }

        if (value.length() > size) {

            value = value.substring(0, size);
        }

        this.value = value;
    }

    public int getSize() {
        return size;
    }

    public String getValue() {
        return value;
    }

    public String toString() {

        int valSize = value.length();

        if (valSize == size) {

            return value;
        }

        return String.valueOf(StoreOptionEnum.FILL_BLACK_MARK).repeat(size - valSize)+ this.value;
    }
}
