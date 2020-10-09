package P7.Data;

import P7.Domein.Adres;
import P7.Domein.OVChipkaart;
import P7.Domein.Product;
import P7.Domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private ProductDAOHibernate pdao;
    private Session session;
    private Transaction tx = null;

    public OVChipkaartDAOHibernate(Session session){
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try{
            tx = session.beginTransaction();
            session.save(ovChipkaart);

            for (Product product : ovChipkaart.getProducten()){
                session.save(product);
            }

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
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        try{
            tx = session.beginTransaction();
            session.update(ovChipkaart);

            for (Product product : ovChipkaart.getProducten()){
                session.update(product);
            }

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
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        try{
            ovChipkaart.deleteOvChipkaart(ovChipkaart);

            tx = session.beginTransaction();
            OVChipkaart mergedChipkaart = (OVChipkaart) session.merge(ovChipkaart);
            session.delete(mergedChipkaart);
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
    public OVChipkaart findById(int id) throws SQLException {
        try{
            String findQuery = "FROM OVChipkaart a WHERE kaart_nummer = '"+ id +"'";
            return session.createQuery(findQuery, OVChipkaart.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        try{
            String findQuery = "FROM OVChipkaart a WHERE reiziger_id = '"+ reiziger.getId() +"'";
            return session.createQuery(findQuery, OVChipkaart.class).getResultList();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) throws SQLException {
        try{
            TypedQuery<OVChipkaart> query = session.createQuery("SELECT e FROM OVChipkaart e JOIN e.producten p WHERE  p.id = :product_id", OVChipkaart.class);
            query.setParameter("product_id", product.getProductNummer());
            return query.getResultList();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        return session.createQuery("SELECT a FROM OVChipkaart a", OVChipkaart.class).getResultList();
    }
}
