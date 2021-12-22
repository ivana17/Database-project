package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.VehicleOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_VehicleOperations implements VehicleOperations{
    
    private Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertVehicle(String licencePlateNumber, int fuelType, BigDecimal fuelConsumtion) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Vozilo (RegBr,TipGoriva,Potrosnja) VALUES (?,?,?)");){
            ps.setString(1, licencePlateNumber);
            ps.setInt(2, fuelType);
            ps.setBigDecimal(3, fuelConsumtion);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int deleteVehicles(String... licencePlateNumbers) {
        int sum = 0;
            for(String plate : licencePlateNumbers) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Vozilo WHERE RegBr=?");){
                ps.setString(1, plate);
                sum+= ps.executeUpdate();
                } catch (SQLException ex) {
            Logger.getLogger(vi160137_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        }
          return sum; 
    }

    @Override
    public List<String> getAllVehichles() {
        List<String> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT RegBr FROM Vozilo");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean changeFuelType(String licencePlateNumber, int fuelType) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Vozilo SET TipGoriva = ? WHERE RegBr = ?");){
            ps.setInt(1, fuelType);
            ps.setString(2, licencePlateNumber);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeConsumption(String licencePlateNumber, BigDecimal fuelConsumption) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Vozilo SET Potrosnja = ? WHERE RegBr = ?");){
            ps.setBigDecimal(1, fuelConsumption);
            ps.setString(2, licencePlateNumber);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
