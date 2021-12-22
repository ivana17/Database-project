package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.UserOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_UserOperations implements UserOperations{
    
    private Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertUser(String userName, String firstName, String lastName, String password) {
        if(!(Character.isUpperCase(firstName.charAt(0)) && Character.isUpperCase(lastName.charAt(0)) && password.length()>=8 && password.matches(".*\\d.*") && password.matches(".*[a-zA-Z]+.*"))){
                System.out.println(firstName + lastName + password);
                return false;
        }
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Korisnik (Ime,Prezime,KorIme,Sifra) VALUES (?,?,?,?)");){
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, userName);
            ps.setString(4, password);
            int value=ps.executeUpdate();
            if(value == 1) return true;            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int declareAdmin(String userName) {
        try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Korisnik WHERE KorIme = ?");){
            ps1.setString(1, userName);
            ResultSet rs=ps1.executeQuery();
            if(rs.next()) {
                PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM Administrator WHERE KorImeAdmin = ?");
                ps2.setString(1, userName);
                ResultSet rs2 = ps2.executeQuery();
                
                if(rs2.next())  return 1;

                PreparedStatement ps = conn.prepareStatement("INSERT INTO Administrator VALUES (?)");
                ps.setString(1,userName);
                int value = ps.executeUpdate();
                
                if(value == 1) return 0;
                    
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }

    @Override
    public Integer getSentPackages(String... userNames) {
        int sum = 0;
            for(String userName : userNames) {
                try (PreparedStatement ps = conn.prepareStatement("SELECT BrPoslPaketa FROM Korisnik WHERE KorIme=?");){
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    sum += rs.getInt(1);
                else
                    return null;
                } catch (SQLException ex) {
            Logger.getLogger(vi160137_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        }
          return sum; 
    }

    @Override
    public int deleteUsers(String... userNames) {
        int sum = 0;
            for(String userName : userNames) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Korisnik WHERE KorIme=?");){
                ps.setString(1, userName);
                sum+= ps.executeUpdate();
                } catch (SQLException ex) {
            Logger.getLogger(vi160137_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        }
          return sum; 
    }

    @Override
    public List<String> getAllUsers() {
        List<String> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT KorIme FROM Korisnik");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
