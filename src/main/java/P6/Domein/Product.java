package P6.Domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;

@Entity
public class Product {
    @Id
    @Column (name = "product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private float prijs;
    // Ik gebruik hier een list van ID's in plaats van de objecten zelf om looping te voorkomen
    @Transient
    private ArrayList<Integer> ovChipkaartenIds = new ArrayList<>();

    public static ArrayList<Product> alleProducten = new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, float prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;

        if(!alleProducten.contains(this)) {
            alleProducten.add(this);
        }
    }

    public Product(){}

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
        this.prijs = prijs;
    }

    public ArrayList<Integer> getOvChipkaarten() {
        return ovChipkaartenIds;
    }

    public void setOvChipkaarten(ArrayList<OVChipkaart> ovChipkaarten) {
        ovChipkaartenIds.clear();
        for (OVChipkaart ovChipkaart : ovChipkaarten){
            if (!ovChipkaartenIds.contains(ovChipkaart.getKaartNummer())){
                ovChipkaartenIds.add(ovChipkaart.getKaartNummer());
            }
        }
    }

    public Boolean addOvChipkaart(OVChipkaart ovChipkaart){
        if (!ovChipkaartenIds.contains(ovChipkaart.getKaartNummer())) {
            this.ovChipkaartenIds.add(ovChipkaart.getKaartNummer());
            return true;
        }
        return false;
    }

    public Boolean deleteProduct(Product product) {
        for (OVChipkaart ovChipkaart : OVChipkaart.alleOvChipkaarten) {
            if (ovChipkaart.getProducten().contains(product)) {
                ovChipkaart.getProducten().remove(product);
            }
        }
        return alleProducten.remove(product);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Product{" + "Nummer = ").append(productNummer).append(", Naam = '").append(naam).append('\'').append(", Beschrijving = '").append(beschrijving).append('\'').append(", Prijs = ").append(prijs).append(", Chipkaarten = [");
        for (int i : ovChipkaartenIds){
            OVChipkaart chipkaart = OVChipkaart.findById(i);
            s.append("{ID: " + chipkaart.getKaartNummer() + ", Geldig Tot: " + chipkaart.getGeldigTot() + ", Saldo: " + chipkaart.getSaldo() + ", Klasse: " + chipkaart.getKlasse() + "}");
        }
        s.append("]}");
        return s.toString();
    }

    public static Product findById(int id) {
        for(Product product : alleProducten){
            if(product.getProductNummer() == id){
                return product;
            }
        }
        return null;
    }
}
