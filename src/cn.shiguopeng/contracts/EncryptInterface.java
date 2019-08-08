package cn.shiguopeng.contracts;

public interface EncryptInterface {

    public String encrypt(String plaintext);

    public String decrypt(String secretKey) throws Exception;
}
