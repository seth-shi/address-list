package cn.shiguopeng.contracts;

public interface EncryptContract {

    public String encrypt(String plaintext);

    public String decrypt(String secretKey) throws Exception;
}
