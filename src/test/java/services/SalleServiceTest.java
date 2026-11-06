package services;
import entities.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateUtil;
import static org.junit.jupiter.api.Assertions.*;

class SalleServiceTest {
    SessionFactory factory;
    SalleService rooms;
    MachineService machines;
    @BeforeEach void open() {
        factory=HibernateUtil.newTestFactory(false);
        rooms=new SalleService(factory);
        machines=new MachineService(factory);
    }
    @AfterEach void close() { factory.close(); }

    @Test void roomCrud() {
        Salle room=new Salle("S2","Reseau");
        assertTrue(rooms.create(room));
        assertEquals("S2",rooms.getById(room.getId()).getCode());
        room.setLibelle("Reseau II");
        assertTrue(rooms.update(room));
        assertEquals("Reseau II",rooms.getAll().get(0).getLibelle());
        assertTrue(rooms.delete(room));
        assertNull(rooms.getById(room.getId()));
    }
    @Test void machineCrudAndBrandFilter() {
        Salle room=new Salle("S1","Informatique");
        assertTrue(rooms.create(room));
        Machine dell=new Machine("M-101","Dell",room);
        Machine hp=new Machine("M-102","HP",room);
        assertTrue(machines.create(dell));
        assertTrue(machines.create(hp));
        assertEquals(room.getId(),machines.getById(dell.getId()).getSalle().getId());
        assertEquals(2,machines.getAll().size());
        assertEquals("M-101",machines.findByMarque("Dell").get(0).getRef());
        dell.setMarque("Lenovo");
        assertTrue(machines.update(dell));
        assertTrue(machines.findByMarque("Dell").isEmpty());
        assertTrue(machines.delete(dell));
        assertNull(machines.getById(dell.getId()));
    }
    @Test void roomCascadeAndFetch() {
        Salle room=new Salle("S3","Labo");
        Machine machine=new Machine("M-103","Dell",room);
        assertTrue(rooms.create(room));
        assertEquals(machine.getId(),rooms.getWithMachines(room.getId()).getMachines().get(0).getId());
        assertTrue(rooms.delete(room));
        assertNull(machines.getById(machine.getId()));
    }
    @Test void failedCascadeRollsBackRoomAndEarlierMachine() {
        Salle room=new Salle("BAD","Rollback");
        new Machine("VALID","Dell",room);
        new Machine(null,"HP",room);
        assertFalse(rooms.create(room));
        assertTrue(rooms.getAll().isEmpty());
        assertTrue(machines.getAll().isEmpty());
        assertTrue(rooms.create(new Salle("OK","After rollback")));
    }
}
