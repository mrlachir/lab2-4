package services;
import dao.*;
import entities.Machine;
import org.hibernate.SessionFactory;
import java.util.List;

public class MachineService implements IDao<Machine> {
    private final MachineDao dao;
    public MachineService() { dao=new MachineDao(); }
    public MachineService(SessionFactory factory) { dao=new MachineDao(factory); }
    public boolean create(Machine entity) { return dao.create(entity); }
    public boolean update(Machine entity) { return dao.update(entity); }
    public boolean delete(Machine entity) { return dao.delete(entity); }
    public Machine getById(int id) { return dao.getById(id); }
    public List<Machine> getAll() { return dao.getAll(); }
    public List<Machine> findByMarque(String marque) { return dao.findByMarque(marque); }
}
