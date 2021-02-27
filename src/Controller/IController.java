package Controller;

import Model.BaseEntity;
import Repository.Repository;

public interface IController {
    public void add();
    public void update();
    public void remove();
    public Repository<Integer, BaseEntity<Integer>> getRepository();
}
