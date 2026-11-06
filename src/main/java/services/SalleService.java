package services;
import dao.*;
import entities.Salle;
import org.hibernate.SessionFactory;
import java.util.List;

public class SalleService implements IDao<Salle> {
    private final SalleDao dao;
    public SalleService() { dao=new SalleDao(); }
    public SalleService(SessionFactory factory) { dao=new SalleDao(factory); }
    public boolean create(Salle entity) { return dao.create(entity); }
    public boolean update(Salle entity) { return dao.update(entity); }
    public boolean delete(Salle entity) { return dao.delete(entity); }
    public Salle getById(int id) { return dao.getById(id); }
    public List<Salle> getAll() { return dao.getAll(); }
    public Salle getWithMachines(int id) { return dao.getWithMachines(id); }
}
