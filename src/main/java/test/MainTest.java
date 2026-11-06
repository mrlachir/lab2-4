package test;
import entities.*;
import services.*;
import util.HibernateUtil;
import org.hibernate.SessionFactory;

public class MainTest {
    public static void main(String[] args) {
        try (SessionFactory factory=HibernateUtil.getSessionFactory()) {
            SalleService rooms=new SalleService(factory);
            MachineService machines=new MachineService(factory);
            Salle room=new Salle("S1","Salle Informatique");
            require(rooms.create(room),"creation salle");
            Machine machine=new Machine();
            machine.setRef("M-101"); machine.setMarque("Dell"); machine.setSalle(room);
            require(machines.create(machine),"creation machine");
            machine.setMarque("HP"); require(machines.update(machine),"mise a jour machine");
            machine.setMarque("Dell"); require(machines.update(machine),"restauration marque");
            Salle loaded=rooms.getWithMachines(room.getId());
            System.out.println("DONNEES INSEREES : "+loaded.getCode()+" / "+loaded.getLibelle());
            loaded.getMachines().forEach(m->System.out.println("MACHINE : "+m.getRef()+" / "+m.getMarque()));
            System.out.println("HQL Dell : "+machines.findByMarque("Dell").size()+" resultat(s)");
            Machine temp=new Machine("TEMP","Lenovo",null);
            require(machines.create(temp)&&machines.delete(temp),"suppression machine temporaire");
            System.out.println("CRUD OK : creation, lecture, modification, suppression");
        }
    }
    private static void require(boolean ok,String action) {
        if(!ok) throw new IllegalStateException("Echec : "+action);
    }
}
