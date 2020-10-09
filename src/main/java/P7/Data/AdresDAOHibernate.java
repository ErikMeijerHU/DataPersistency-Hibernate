package P7.Data;

import P7.Domein.Adres;
import P7.Domein.Reiziger;
import P7.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private ReizigerDAOHibernate rdao;
    private Session session;
    private Transaction tx = null;

    public AdresDAOHibernate(Session session){
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        try{
            tx = session.beginTransaction();
            session.saveOrUpdate(adres);
            tx.commit();
            return true;
        }
        catch (PersistenceException e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        try{
            tx = session.beginTransaction();
            session.update(adres);
            tx.commit();
            return true;
        }
        catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        try{
            this.rdao = new ReizigerDAOHibernate(session);

            tx = session.beginTransaction();
            session.delete(adres);
            tx.commit();

            if(adres.getReiziger() != null) {
                Reiziger reiziger = adres.getReiziger();
                reiziger.removeAdres();
                rdao.delete(reiziger);
            }
            return true;
        }
        catch (Exception e){
            tx.rollback();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Adres findById(int id) throws SQLException {
        try{
            String findQuery = "FROM Adres a WHERE adres_id = '"+ id +"'";
            return session.createQuery(findQuery, Adres.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try{
            String findQuery = "FROM Adres a WHERE reiziger_id = '"+ reiziger.getId() +"'";
            return session.createQuery(findQuery, Adres.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        return session.createQuery("SELECT a FROM Adres a", Adres.class).getResultList();
    }
}
