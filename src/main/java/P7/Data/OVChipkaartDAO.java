package P7.Data;

import P7.Domein.OVChipkaart;
import P7.Domein.Product;
import P7.Domein.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovChipkaart) throws SQLException;
    public boolean update(OVChipkaart ovChipkaart) throws SQLException;
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException;
    public OVChipkaart findById(int id) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findByProduct(Product product) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
}
