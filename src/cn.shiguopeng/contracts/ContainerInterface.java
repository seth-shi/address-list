package cn.shiguopeng.contracts;

public interface ContainerInterface {

    public void register(Object object);
    public Object make(Class cls);
}
