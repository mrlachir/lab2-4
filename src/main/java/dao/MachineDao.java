package dao;
import entities.Machine;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.List;

public class MachineDao extends AbstractDao<Machine> {
    public MachineDao() { this(HibernateUtil.getSessionFactory()); }
    public MachineDao(SessionFactory factory) { super(factory,Machine.class); }
    public List<Machine> findByMarque(String marque) {
        return read(s->s.createQuery("from Machine m where m.marque = :marque order by m.id",Machine.class)
            .setParameter("marque",marque).list());
    }
}
