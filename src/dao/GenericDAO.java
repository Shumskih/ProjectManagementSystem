package dao;

public interface GenericDAO<T,ID> {
    void save(T t);

    T getById(int id);

    void getAll();

    void update(T t);

    void delete(int id);
}
