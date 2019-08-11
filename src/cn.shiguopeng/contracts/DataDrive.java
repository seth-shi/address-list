package cn.shiguopeng.contracts;

public interface DataDrive {

    boolean create(Model model);
    boolean delete(Model model);
    boolean update(Model oldModel, Model newModel);
    Model first(Model model);
}
