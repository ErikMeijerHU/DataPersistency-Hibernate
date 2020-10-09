package P7;


import P7.Data.*;
import P7.Domein.Adres;
import P7.Domein.OVChipkaart;
import P7.Domein.Product;
import P7.Domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException, ParseException {
        //testFetchAll();
        Session session = getSession();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        AdresDAOHibernate adao = new AdresDAOHibernate(session);
        OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);
        ProductDAOHibernate pdao = new ProductDAOHibernate(session);

        testDAOHibernate(rdao,adao,ovdao,pdao);

        session.close();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testDAOHibernate(ReizigerDAO rdao, AdresDAO adao, OVChipkaartDAO ovdao, ProductDAO pdao) throws SQLException, ParseException{
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        String gbdatumstring = "1981-03-14";
        Date gbdatum = format.parse(gbdatumstring);
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", gbdatum);

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        System.out.println("");

        // Zoek reiziger met ID

        System.out.println("[Test] Reiziger zoeken met id 77 zou S Boers moeten geven:");
        System.out.println(rdao.findById(77));
        System.out.println("");

        // Zoek reiziger met Geboortedatum

        System.out.println("[Test] Reiziger zoeken met geboortedatum 1981-03-14 zou S Boers moeten geven:");
        System.out.println(rdao.findByGbdatum(format.parse("1981-03-14")));
        System.out.println("");

        // Update bestaande reiziger

        System.out.println("[Test] Reiziger met ID 77 heeft eerst achternaam:");
        Reiziger reiziger = rdao.findById(77);
        System.out.println(reiziger.getAchternaam());
        reiziger.setAchternaam("Klaas");
        System.out.println("En na update:");
        rdao.update(reiziger);
        System.out.println(rdao.findById(77).getAchternaam());
        System.out.println("");

        // Delete reiziger

        reizigers = rdao.findAll();
        System.out.print("[Test] Eerst:" + reizigers.size() + " reizigers, na Delete: ");
        rdao.delete(rdao.findById(77));
        reizigers = null;
        reizigers = rdao.findAll();
        System.out.print(reizigers.size());


        System.out.println("");
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen uit de database

        List<Adres> alleAdressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : alleAdressen){
            System.out.println(a);
        }
        System.out.println();

        // Maak nieuw adres aan en persisteer in database, hiervoor moet ook een nieuwe reiziger gemaakt worden vanwege de 1=1 relatie

        Reiziger nieuweReiziger = new Reiziger(6, "B", null, "Geerts", format.parse("1999-01-04"));
        Adres nieuwAdres = new Adres(6, "3827KX", "8A", "Leukestraat", "Utrecht");
        nieuweReiziger.addAdres(nieuwAdres);
        System.out.print("[Test] Eerst " + alleAdressen.size() + " adressen, na AdresDAO.save() ");
        rdao.save(nieuweReiziger);
        adao.save(nieuwAdres);
        alleAdressen = adao.findAll();
        System.out.print(alleAdressen.size() + " adressen\n");
        System.out.println("Nieuw adres en reiziger: "+ nieuweReiziger);
        System.out.println("");

        // Zoek adres met ID

        System.out.println("[Test] Adres zoeken met id 6 zou Leukestraat 8A moeten geven:");
        System.out.println(adao.findById(6));
        System.out.println("");

        // Zoek adres met reiziger

        System.out.println("[Test] Adres zoeken met reiziger B Geerts zou Leukestraat 8A moeten geven:");
        System.out.println(adao.findByReiziger(nieuweReiziger));
        System.out.println("");

        // Update bestaand adres

        System.out.println("[Test] Adres met ID 6 heeft eerst straat:");
        Adres adres = adao.findById(6);
        System.out.println(adres.getStraat());
        adres.setStraat("Anderestraat");
        System.out.println("En na update:");
        adao.update(adres);
        System.out.println(adao.findById(6).getStraat());
        System.out.println("");

        // Verwijder adres, wat ook bijbehorende reiziger verwijderd

        alleAdressen = adao.findAll();
        List<Reiziger> alleReizigers = rdao.findAll();
        System.out.print("[Test] Eerst:" + alleAdressen.size() + " adressen en "+ alleReizigers.size()+" reizigers, na Delete: ");
        adao.delete(adao.findById(6));
        alleAdressen = null;
        alleReizigers = null;
        alleAdressen = adao.findAll();
        alleReizigers = rdao.findAll();
        System.out.print(alleAdressen.size() + " adressen en " + alleReizigers.size() + " reizigers.");
        System.out.println();
        System.out.println();


        System.out.println("\n---------- Test ProductDAO + OVChipkaartDAO -------------");

        // Haal alle Producten uit de database:
        System.out.println("");
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        List<Product> alleProducten = pdao.findAll();
        List<OVChipkaart> alleChipkaarten = ovdao.findAll();
        alleProducten.forEach(System.out::println);

        // Maak nieuw Product en OV Chip aan en sla ze allebei op met pdao.save();
        System.out.println("");
        System.out.print("[Test] Eerst " + alleProducten.size() + " producten en " + alleChipkaarten.size() + " chipkaarten, na ProductDAO.save() ");
        Product nieuwProduct = new Product(77, "Test", "Dit is een test", (float) 12.99);
        OVChipkaart nieuweChipkaart = new OVChipkaart(39687, format.parse("2021-01-05"), 2, (float) 30.00, rdao.findById(3));
        nieuwProduct.addOvChipkaart(nieuweChipkaart);
        nieuweChipkaart.addProduct(nieuwProduct);
        pdao.save(nieuwProduct);
        alleChipkaarten = null;
        alleProducten = null;
        alleProducten = pdao.findAll();
        alleChipkaarten = ovdao.findAll();
        System.out.print(alleProducten.size() + " producten en " + alleChipkaarten.size() + " chipkaarten.\n");
        System.out.println("");
        // Zoek producten met ov chipkaart.
        System.out.println("[Test] Alle producten zoeken bij chipkaart 35283 zou drie resultaten moeten geven:");
        System.out.println("       Aantal resultaten: " + pdao.findByOvChipkaart(ovdao.findById(35283)).size());
        System.out.println("");
        // Aanpassing doen aan de nieuwe chipkaart en product, daarna updaten.
        System.out.println("[Test] ToString van product 77 geeft nu:\n" + nieuwProduct.toString() + "\nEn de ToString van chipkaart 39687 geeft:\n" + nieuweChipkaart.toString()+"\nNa update:");
        nieuwProduct.setPrijs((float) 49.99);
        nieuweChipkaart.setSaldo(20000);
        pdao.update(nieuwProduct);
        ovdao.update(nieuweChipkaart);
        System.out.println(nieuwProduct.toString() + "\n" + nieuweChipkaart.toString());
        System.out.println("");
        // Verwijder product en zie wat het doet met de bijbehorende chipkaart
        System.out.print("[Test] Eerst " + alleProducten.size() + " producten en chipkaart 35283 heeft " + nieuweChipkaart.getProducten().size() + " gelinkde producten, na delete ");
        pdao.delete(nieuwProduct);
        alleChipkaarten = null;
        alleProducten = null;
        alleProducten = pdao.findAll();
        alleChipkaarten = ovdao.findAll();
        System.out.print(alleProducten.size() + " producten en de chipkaart heeft " + nieuweChipkaart.getProducten().size() + " gelinkde producten.\n");
        ovdao.delete(nieuweChipkaart);
    }
}
