package dao;
import org.hibernate.*;
import java.util.List;
import java.util.function.Function;
import java.util.logging.*;

public abstract class AbstractDao<T> implements IDao<T> {
    private static final Logger LOG=Logger.getLogger(AbstractDao.class.getName());
    private final SessionFactory factory;
    private final Class<T> type;
    protected AbstractDao(SessionFactory factory,Class<T> type) { this.factory=factory; this.type=type; }
    protected <R> R read(Function<Session,R> action) {
        try(Session session=factory.openSession()) { return action.apply(session); }
    }
    protected boolean write(Function<Session,Boolean> action) {
        try(Session session=factory.openSession()) {
            Transaction tx=session.beginTransaction();
            try {
                boolean result=action.apply(session);
                tx.commit();
                return result;
            } catch(RuntimeException ex) {
                if(tx.isActive()) try { tx.rollback(); }
                catch(RuntimeException rollback) { ex.addSuppressed(rollback); }
                throw ex;
            }
        } catch(RuntimeException ex) {
            LOG.log(Level.WARNING,"Write failed for "+type.getSimpleName(),ex);
            return false;
        }
    }
    public boolean create(T entity) {
        return write(s->{s.persist(entity); return true;});
    }
    public boolean update(T entity) {
        return write(s->{
            Object id=factory.getPersistenceUnitUtil().getIdentifier(entity);
            if(s.find(type,id)==null) return false;
            s.merge(entity);
            return true;
        });
    }
    public boolean delete(T entity) {
        return write(s->{
            Object id=factory.getPersistenceUnitUtil().getIdentifier(entity);
            T managed=s.find(type,id);
            if(managed==null) return false;
            s.remove(managed);
            return true;
        });
    }
    public T getById(int id) { return read(s->s.get(type,id)); }
    public List<T> getAll() {
        return read(s->s.createQuery("from "+type.getSimpleName()+" order by id",type).list());
    }
}
