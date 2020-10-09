package P7.Data;

import P7.Domein.Adres;
import P7.Domein.OVChipkaart;
import P7.Domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private OVChipkaartDAOHibernate ovdao;
    private AdresDAOHibernate adao;
    private Session session;
    private Transaction tx = null;

    public ReizigerDAOHibernate(Session session){
        this.session = session;

    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try{
            this.ovdao = new OVChipkaartDAOHibernate(session);
            this.adao = new AdresDAOHibernate(session);

            tx = session.beginTransaction();
            session.saveOrUpdate(reiziger);
            tx.commit();

            if(reiziger.getOVChipkaarten().size()>0) {
                for (OVChipkaart ovChipkaart : reiziger.getOVChipkaarten()) {
                    ovdao.save(ovChipkaart);
                }
            }
            return true;
        }
        catch (PersistenceException e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        try{
            this.ovdao = new OVChipkaartDAOHibernate(session);
            this.adao = new AdresDAOHibernate(session);

            tx = session.beginTransaction();
            session.update(reiziger);
            tx.commit();

            if(reiziger.getOVChipkaarten().size()>0) {
                for (OVChipkaart ovChipkaart : reiziger.getOVChipkaarten()) {
                    ovdao.update(ovChipkaart);
                }
            }
            return true;
        }
        catch (PersistenceException e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        try{
            this.ovdao = new OVChipkaartDAOHibernate(session);
            this.adao = new AdresDAOHibernate(session);

            tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();

            if(reiziger.getAdres() != null) {
                Adres adres = reiziger.getAdres();
                adres.setReiziger(null);
                adao.delete(adres);
            }

            return true;
        }
        catch (PersistenceException e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        try{
            String findQuery = "FROM Reiziger a WHERE reiziger_id = '"+ id +"'";
            return session.createQuery(findQuery, Reiziger.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Reiziger findByGbdatum(Date geboortedatum) throws SQLException {
        try{
            String findQuery = "FROM Reiziger a WHERE geboortedatum = '"+ geboortedatum +"'";
            return session.createQuery(findQuery, Reiziger.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        return session.createQuery("SELECT a FROM Reiziger a", Reiziger.class).getResultList();
    }
}
