package server.interfaces;

import java.util.List;

public interface DAO<T> {
    T getById(int i);
    T getFindByParam(T t);
    List<T> getAll();
    void create(T t);
    void remove(T t);
    void edit(T t);
}
