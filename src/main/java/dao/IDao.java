package dao;
import java.util.List;

public interface IDao<T> {
    boolean create(T entity);
    boolean delete(T entity);
    boolean update(T entity);
    T getById(int id);
    List<T> getAll();
}
