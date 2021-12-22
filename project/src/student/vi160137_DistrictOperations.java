package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.DistrictOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_DistrictOperations implements DistrictOperations{

    private Connection conn = DB.getInstance().getConnection();
    
    @Override
    public int insertDistrict(String name, int cityId, int xCord, int yCord) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Opstina (Naziv,Xkoord,Ykoord,IdG) VALUES (?,?,?,?)");){
            ps.setString(1, name);       
            ps.setInt(2, xCord);
            ps.setInt(3, yCord);
            ps.setInt(4, cityId);
            int value=ps.executeUpdate();
            if(value == 1) {
                PreparedStatement ps1 = conn.prepareStatement("SELECT MAX(IdO) FROM Opstina");
                ResultSet rs = ps1.executeQuery();
                if(rs.next())
                {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int deleteDistricts(String... districts) {
        int sum = 0;
            for(String district : districts) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Opstina WHERE Naziv=?");){
                ps.setString(1, district);
                sum+= ps.executeUpdate();
                } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        }
          return sum; 
    }

    @Override
    public boolean deleteDistrict(int i) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Opstina WHERE IdO=?");){
            ps.setInt(1, i);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int deleteAllDistrictsFromCity(String nameOfTheCity) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE Opstina FROM Opstina \n" +
"	JOIN Grad ON Opstina.IdG = Grad.IdG WHERE Grad.Naziv=?");){
            ps.setString(1, nameOfTheCity);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public List<Integer> getAllDistrictsFromCity(int idCity) {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Opstina WHERE IdG=?");){
            ps.setInt(1, idCity);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getInt(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getAllDistricts() {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Opstina");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getInt(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
