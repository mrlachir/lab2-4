package entities;
import javax.persistence.*;

@Entity @Table(name="machines")
public class Machine {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false) private String ref;
    @Column(nullable=false) private String marque;
    @ManyToOne @JoinColumn(name="salle_id")
    private Salle salle;

    public Machine() {}
    public Machine(String ref, String marque, Salle salle) {
        this.ref=ref; this.marque=marque; setSalle(salle);
    }
    public int getId() { return id; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref=ref; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque=marque; }
    public Salle getSalle() { return salle; }
    public void setSalle(Salle next) {
        if (salle==next) return;
        Salle previous=salle; salle=next;
        if (previous!=null) previous.detach(this);
        if (next!=null) next.attach(this);
    }
}
