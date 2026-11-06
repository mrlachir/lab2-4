package entities;
import javax.persistence.*;
import java.util.*;

@Entity @Table(name="salles")
public class Salle {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false) private String code;
    @Column(nullable=false) private String libelle;
    @OneToMany(mappedBy="salle", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Machine> machines = new ArrayList<>();

    public Salle() {}
    public Salle(String code, String libelle) { this.code=code; this.libelle=libelle; }
    public int getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code=code; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle=libelle; }
    public List<Machine> getMachines() { return Collections.unmodifiableList(machines); }
    public void addMachine(Machine machine) { Objects.requireNonNull(machine).setSalle(this); }
    void attach(Machine machine) { if (!machines.contains(machine)) machines.add(machine); }
    void detach(Machine machine) { machines.remove(machine); }
}
