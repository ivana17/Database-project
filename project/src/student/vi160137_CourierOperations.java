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
import rs.etf.sab.operations.CourierOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_CourierOperations implements CourierOperations{
    
    private Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertCourier(String courierUserName, String licencePlateNumber) {
        try (PreparedStatement ps1 = conn.prepareStatement("SELECT KorIme FROM Korisnik WHERE KorIme = ?");){
            ps1.setString(1, courierUserName);
            ResultSet rs=ps1.executeQuery();
            if(rs.next()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO Kurir (IdKur,RegBr) VALUES (?,?)");
                ps.setInt(1, rs.getInt(1));
                ps.setString(2, licencePlateNumber);
                int value = ps.executeUpdate();
                
                if(value == 1)
                    return true;
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourier(String courierUserName) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE Kurir FROM Kurir WHERE KorImeKur = ?");){
            ps.setString(1, courierUserName);
            int value = ps.executeUpdate();

            if(value == 1)
                return true;
            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getCouriersWithStatus(int statusOfCourier) {
        List<String> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Kurir WHERE Status = ?");){
            ps.setInt(1, statusOfCourier);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<String> getAllCouriers() {
        List<String> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Kurir");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getString("KorImeKur"));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getAverageCourierProfit(int numberOfDeliveries) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT AVG(Profit) FROM Kurir WHERE BrISpPaketa > ?");){
            ps.setInt(1, numberOfDeliveries);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBigDecimal(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
