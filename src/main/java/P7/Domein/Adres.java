package P7.Domein;

import javax.persistence.*;

@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    @OneToOne
    @JoinColumn(name = "reiziger_id", referencedColumnName = "reiziger_id")
    private Reiziger reiziger;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public Adres(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "Adres {" +
                "#" + id +
                ", " + postcode +
                ", " + straat + " " + huisnummer + ", " + woonplaats +
                '}';
    }
}
