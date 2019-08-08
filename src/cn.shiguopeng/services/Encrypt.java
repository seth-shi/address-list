package cn.shiguopeng.services;

import cn.shiguopeng.contracts.EncryptInterface;

final public class Encrypt implements EncryptInterface {

    private String key = "I love you not because of who you are, but because of who I am when I am with you.";
    private int keyLength = key.length();

    public Encrypt(FileManager fileManager) {

        keyLength = key.length();
    }


    public String encrypt(String plaintext) {

        byte originAscII = -1;
        byte keyAscII = -1;
        int sumAscII = -1;

        int keyOffset = 0;

        int plainLength = plaintext.length();

        // 容量即为加密字符串的两倍大小
        StringBuilder secretKey = new StringBuilder(plainLength * 2);

        for (int i = 0; i < plainLength; ++i, ++keyOffset) {

            if (keyOffset == keyLength) {
                keyOffset = 0;
            }

            originAscII = (byte) plaintext.charAt(i);
            keyAscII = (byte) key.charAt(keyOffset);

            sumAscII = originAscII + keyAscII;
            if (sumAscII > 127) {
                sumAscII -= 127;
            }

            String hex = Integer.toHexString(sumAscII);
            if (hex.length() == 1) {

                hex = "0" + hex;
            }
            secretKey.append(hex);
        }

        return secretKey.toString();
    }

    public String decrypt(String secretKey) {

        byte originAscII = -1;
        byte keyAscII = -1;
        int sumAscII = -1;

        int keyOffset = 0;
        int secretLength = secretKey.length();

        if (secretLength % 2 != 0) {
            return null;
        }

        // 容量即为加密字符串的两倍大小
        StringBuilder plaintext = new StringBuilder(secretLength / 2);

        for (int i = 0; i < secretLength; i += 2, ++keyOffset) {

            if (keyOffset == keyLength) {
                keyOffset = 0;
            }

            // 120 +10 =3   3-10 = -7

            // 将十六进制转换为十进制
            originAscII = Byte.parseByte(secretKey.substring(i, i + 2), 16);
            keyAscII = (byte) key.charAt(keyOffset);

            sumAscII = originAscII - keyAscII;
            if (sumAscII < 0) {
                sumAscII += 127;
            }


            plaintext.append((char) sumAscII);
        }

        return plaintext.toString();
    }
}
