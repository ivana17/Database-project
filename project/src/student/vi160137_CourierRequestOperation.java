package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierRequestOperation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_CourierRequestOperation implements CourierRequestOperation{
    
    private Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertCourierRequest(String userName, String licencePlateNumber) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO KurirZahtev (KorIme,RegBr) VALUES (?,?)");){
            ps.setString(1, userName);
            ps.setString(2, licencePlateNumber);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String userName) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE KurirZahtev FROM KurirZahtev WHERE KorIme=?");){
            ps.setString(1, userName);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeVehicleInCourierRequest(String userName, String licencePlateNumber) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE KurirZahtev SET RegBr=? WHERE KorIme=?");){
            ps.setString(2, userName);
            ps.setString(1, licencePlateNumber);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<String> getAllCourierRequests() {
        List<String> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT KorIme FROM KurirZahtev");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean grantRequest(String username) {
        try (PreparedStatement ps1 = conn.prepareStatement("EXEC dbo.spOdobriZahtev @IdK = ?");){
            ps1.setString(1, username);
            int value = ps1.executeUpdate();
            if(value == 1){                
                return true;              
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
