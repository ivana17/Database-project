package student;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author vi160137
 */
public class vi160137_CityOperations implements CityOperations {

    private Connection conn = DB.getInstance().getConnection();
    
    @Override
    public int insertCity(String name, String postalCode) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Grad (Naziv,PostBroj) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);){
            ps.setString(1, name);
            ps.setString(2, postalCode);
            int value = ps.executeUpdate();
            if(value == 0){
                return -1;
            }
            
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()){
                    return (int)keys.getLong(1);
                    }
                
//                PreparedStatement ps1 = conn.prepareStatement("SELECT MAX(IdG) FROM Grad");
//                ResultSet rs = ps1.executeQuery();
//                if(rs.next())
//                {
//                    return rs.getInt(1);
//                }
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int deleteCity(String... strings) {
            int sum = 0;
            for(String city : strings) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Grad WHERE Naziv=?");){
                ps.setString(1, city);
                //sum+= ps.executeUpdate();
                int b = ps.executeUpdate();
                if(b != 0){
                    sum++;
                }
                } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        }
          return sum; 
    }

    @Override
    public boolean deleteCity(int idCity) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Grad WHERE IdG=?");){
            ps.setInt(1, idCity);
            if (ps.executeUpdate() > 0) return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        }

    @Override
    public List<Integer> getAllCities() {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Grad");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getInt(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
