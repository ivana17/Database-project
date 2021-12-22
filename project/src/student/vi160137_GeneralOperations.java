package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.GeneralOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_GeneralOperations implements GeneralOperations{
    
    private Connection conn = DB.getInstance().getConnection();

    @Override
    public void eraseAll() {
        try (PreparedStatement ps = conn.prepareStatement("ALTER TABLE Korisnik NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Administrator NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Kurir NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Grad NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE KurirZahtev NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Opstina NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Paket NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Ponuda NOCHECK CONSTRAINT ALL\n" +
"ALTER TABLE Vozilo NOCHECK CONSTRAINT ALL\n" +
"\n" +
"ALTER TABLE Korisnik DISABLE TRIGGER ALL\n" +
"ALTER TABLE Administrator DISABLE TRIGGER ALL\n" +
"ALTER TABLE Kurir DISABLE TRIGGER ALL\n" +
"ALTER TABLE Grad DISABLE TRIGGER ALL\n" +
"ALTER TABLE KurirZahtev DISABLE TRIGGER ALL\n" +
"ALTER TABLE Opstina DISABLE TRIGGER ALL\n" +
"ALTER TABLE Paket DISABLE TRIGGER ALL\n" +
"ALTER TABLE Ponuda DISABLE TRIGGER ALL\n" +
"ALTER TABLE Vozilo DISABLE TRIGGER ALL\n" +
"\n" +
"DELETE FROM Administrator\n" +
"DELETE FROM Grad\n" +
"DELETE FROM KurirZahtev\n" +
"DELETE FROM Opstina\n" +
"DELETE FROM Paket\n" +
"DELETE FROM Ponuda\n" +
"DELETE FROM Vozilo\n" +
"DELETE FROM Korisnik\n" +
"DELETE FROM Kurir\n" +
"\n" +
"ALTER TABLE Korisnik ENABLE TRIGGER ALL\n" +
"ALTER TABLE Administrator ENABLE TRIGGER ALL\n" +
"ALTER TABLE Kurir ENABLE TRIGGER ALL\n" +
"ALTER TABLE Grad ENABLE TRIGGER ALL\n" +
"ALTER TABLE KurirZahtev ENABLE TRIGGER ALL\n" +
"ALTER TABLE Opstina ENABLE TRIGGER ALL\n" +
"ALTER TABLE Paket ENABLE TRIGGER ALL\n" +
"ALTER TABLE Ponuda ENABLE TRIGGER ALL\n" +
"ALTER TABLE Vozilo ENABLE TRIGGER ALL\n" +
"\n" +
"ALTER TABLE Korisnik WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Administrator WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Kurir WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Grad WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE KurirZahtev WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Opstina WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Paket WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Ponuda WITH CHECK CHECK CONSTRAINT ALL\n" +
"ALTER TABLE Vozilo WITH CHECK CHECK CONSTRAINT ALL\n" +
"DBCC CHECKIDENT (Grad, RESEED, 0)\n" +
"DBCC CHECKIDENT (Opstina, RESEED, 0)\n" +
"DBCC CHECKIDENT (Paket, RESEED, 0)\n" +
"DBCC CHECKIDENT (Ponuda, RESEED, 0)");){
            ps.executeUpdate();           
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
            
}
