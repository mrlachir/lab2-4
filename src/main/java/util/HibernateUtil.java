package util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AvailableSettings;
import java.util.UUID;

public final class HibernateUtil {
    private HibernateUtil() {}
    private static final class Holder {
        private static final SessionFactory FACTORY =
            "h2".equals(System.getProperty("lab.db")) ? newTestFactory(true) : mysql();
    }
    public static SessionFactory getSessionFactory() { return Holder.FACTORY; }
    private static SessionFactory mysql() {
        Configuration cfg=new Configuration().configure();
        override(cfg,AvailableSettings.URL,"DB_URL");
        override(cfg,AvailableSettings.USER,"DB_USER");
        override(cfg,AvailableSettings.PASS,"DB_PASSWORD");
        return cfg.buildSessionFactory();
    }
    private static void override(Configuration cfg,String key,String env) {
        String value=System.getenv(env);
        if(value!=null) cfg.setProperty(key,value);
    }
    public static SessionFactory newTestFactory(boolean showSql) {
        return new Configuration().configure()
            .setProperty(AvailableSettings.DRIVER,"org.h2.Driver")
            .setProperty(AvailableSettings.URL,"jdbc:h2:mem:lab4_"+UUID.randomUUID())
            .setProperty(AvailableSettings.USER,"sa")
            .setProperty(AvailableSettings.PASS,"")
            .setProperty(AvailableSettings.DIALECT,"org.hibernate.dialect.H2Dialect")
            .setProperty(AvailableSettings.HBM2DDL_AUTO,"create-drop")
            .setProperty(AvailableSettings.SHOW_SQL,Boolean.toString(showSql))
            .buildSessionFactory();
    }
}
