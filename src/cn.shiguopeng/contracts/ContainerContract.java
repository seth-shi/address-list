package cn.shiguopeng.contracts;

public interface ContainerContract {

    public void register(Object object);
    public Object make(Class cls) throws Exception;
}
