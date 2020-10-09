package P7.Data;

import P7.Domein.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public Reiziger findByGbdatum(Date geboortedatum) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
