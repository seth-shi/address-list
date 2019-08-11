package cn.shiguopeng.contracts;

public interface DataDrive {

    void create(Model model);
    void delete(Model model);
    void update(Model model);

    Model first(Model model);
}
