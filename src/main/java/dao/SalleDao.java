package dao;
import entities.Salle;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class SalleDao extends AbstractDao<Salle> {
    public SalleDao() { this(HibernateUtil.getSessionFactory()); }
    public SalleDao(SessionFactory factory) { super(factory,Salle.class); }
    public Salle getWithMachines(int id) {
        return read(s->s.createQuery("select distinct r from Salle r left join fetch r.machines where r.id=:id",Salle.class)
            .setParameter("id",id).uniqueResult());
    }
}
