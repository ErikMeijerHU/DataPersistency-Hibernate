package P7.Data;


import P7.Domein.OVChipkaart;
import P7.Domein.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;
    private Transaction tx = null;
    private OVChipkaartDAOHibernate ovdao;

    public ProductDAOHibernate(Session session){
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try{
            tx = session.beginTransaction();
            session.save(product);

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()){
                session.save(ovChipkaart);
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
    public boolean update(Product product) {
        try{
            tx = session.beginTransaction();
            session.update(product);

            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()){
                session.update(ovChipkaart);
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
    public boolean delete(Product product) {
        try{
            tx = session.beginTransaction();
            product.deleteProduct(product);

            Product mergedProduct = (Product) session.merge(product);
            session.delete(mergedProduct);
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
    public Product findById(int id) {
        try{
            String findQuery = "FROM Product a WHERE product_nummer = '"+ id +"'";
            return session.createQuery(findQuery, Product.class).getSingleResult();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) {
        try{
            TypedQuery<Product> query = session.createQuery("SELECT p FROM Product p JOIN p.ovChipkaarten o WHERE  o.id = :ov_chipkaart_id", Product.class);
            query.setParameter("ov_chipkaart_id", ovChipkaart.getKaartNummer());
            return query.getResultList();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        return session.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }
}
