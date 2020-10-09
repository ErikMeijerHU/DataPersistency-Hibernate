package P7.Domein;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id")
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    @OneToOne(mappedBy = "reiziger")
    private Adres adres;

    @OneToMany(mappedBy = "reiziger",cascade = {CascadeType.ALL})
    private Set<OVChipkaart> OVChipkaarten = new HashSet<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
    public Reiziger(){}

    public void addAdres(Adres adres){
        adres.setReiziger(this);
        this.adres = adres;
    }

    public void removeAdres(){
        if(adres!=null){
            adres.setReiziger(null);
            this.adres = null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Set<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(Set<OVChipkaart> OVChipkaarten) {
        this.OVChipkaarten = OVChipkaarten;
    }

    public String getOvChipkaartenString() {
        if (getOVChipkaarten().size()!=0){
            String returnString = "OV Chipkaarten:";
            for (OVChipkaart ovChipkaart : getOVChipkaarten()){
                returnString = returnString + ovChipkaart.toString();
            }
            return returnString;
        }
        else{
            return "Geen OV Chipkaarten";
        }
    }
    public String toString() {
        if (adres!=null) {
            if (this.tussenvoegsel != null) {
                return "Reiziger {#" + id + ": " + voorletters + " " + tussenvoegsel + " " + achternaam + " (" + geboortedatum.toString() + "), " + adres.toString() +", "+getOvChipkaartenString()+"}";
            } else {
                return "Reiziger {#" + id + ": " + voorletters + " " + achternaam + " (" + geboortedatum.toString() + "), " + adres.toString() + ", "+getOvChipkaartenString()+"}";
            }
        }
        else {
            if (this.tussenvoegsel != null) {
                return "Reiziger {#" + id + ": " + voorletters + " " + tussenvoegsel + " " + achternaam + " (" + geboortedatum.toString() + "), Geen Adres, "+getOvChipkaartenString()+"}";
            } else {
                return "Reiziger {#" + id + ": " + voorletters + " " + achternaam + " (" + geboortedatum.toString() + "), Geen Adres, "+getOvChipkaartenString()+"}";
            }
        }
    }
}
