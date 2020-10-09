package P7.Data;

import P7.Domein.OVChipkaart;
import P7.Domein.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);
    public Product findById(int id);
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart);
    public List<Product> findAll();
}
