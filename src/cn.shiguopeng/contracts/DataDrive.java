package cn.shiguopeng.contracts;

import cn.shiguopeng.foundtions.ModelFactory;

import java.util.ArrayList;

public interface DataDrive {

    boolean create(Model model);
    boolean delete(Model model);
    boolean update(Model oldModel, Model newModel);
    Model first(Model model);
    ArrayList<Model> get(Model model, int page);
}
