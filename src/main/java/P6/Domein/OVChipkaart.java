package P6.Domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class OVChipkaart {
    @Id
    @Column (name = "kaart_nummer")
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private float saldo;
    @Transient
    private Reiziger reiziger;
    @Transient
    private ArrayList<Product> producten = new ArrayList<Product>();

    public static ArrayList<OVChipkaart> alleOvChipkaarten = new ArrayList<>();

    public OVChipkaart(int kaartNummer, Date geldig_tot, int klasse, float saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;

        if(!alleOvChipkaarten.contains(this)){
            alleOvChipkaarten.add(this);
        }
    }

    public OVChipkaart(){}

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldig_tot) {
        this.geldigTot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public ArrayList<Product> getProducten() {return producten;}

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }

    public Boolean deleteOvChipkaart(OVChipkaart ovChipkaart) {
        // Bij alle producten de OV Chipkaart uit de lijst met chipkaarten halen.
        for (Product product : Product.alleProducten) {
            product.getOvChipkaarten().remove(Integer.valueOf(ovChipkaart.kaartNummer));
        }
        // Chipkaart uit de lijst met alle chipkaarten halen.
        alleOvChipkaarten.remove(ovChipkaart);

        // Chipkaart bij de reiziger eruit halen.
        return ovChipkaart.getReiziger().getOVChipkaarten().remove(ovChipkaart);
    }

    public Boolean addProduct(Product product){
        return this.producten.add(product);
    }

    public static OVChipkaart findById(int id){
        for(OVChipkaart ovChipkaart : alleOvChipkaarten){
            if(ovChipkaart.getKaartNummer() == id){
                return ovChipkaart;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("OVChipkaart {" + "Kaart Nummer=").append(kaartNummer).append(", Geldig Tot=").append(geldigTot).append(", Klasse=").append(klasse).append(", Saldo=").append(saldo).append(", Producten = [");
        for(Product product : producten){
            s.append("{ID: ").append(product.getProductNummer()).append(", Naam: ").append(product.getNaam()).append(", Beschrijving: ").append(product.getBeschrijving()).append(", Prijs: ").append(product.getPrijs()).append("}");
        }
        s.append("]}");
        return s.toString();
    }
}