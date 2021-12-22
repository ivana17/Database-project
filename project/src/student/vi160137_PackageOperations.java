package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.PackageOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author vi160137d
 */
public class vi160137_PackageOperations implements PackageOperations{
    
    private Connection conn = DB.getInstance().getConnection();
    
    @Override
    public int insertPackage(int districtFrom, int districtTo, String userName, int packageType, BigDecimal weight) {
       
        BigDecimal daisy = calculatePrice(districtFrom, districtTo, packageType, weight);
        
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Paket (Cena,Tip,Tezina,IdOpsOd,IdOpsDo,KorIme) VALUES (?,?,?,?,?,?)");){
            ps.setBigDecimal(1,daisy);
            ps.setInt(2, packageType);
            ps.setBigDecimal(3, weight);
            ps.setInt(4, districtFrom);
            ps.setInt(5, districtTo);
            ps.setString(6, userName);
            int value=ps.executeUpdate();
            if(value == 1) {
                PreparedStatement ps1 = conn.prepareStatement("SELECT MAX(IdPak) FROM Paket");
                ResultSet rs = ps1.executeQuery();
                if(rs.next())
                {
                    return rs.getInt(1);
                }
            }
        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int insertTransportOffer(String couriersUserName, int packageId, BigDecimal pricePercentage) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Ponuda (Procenat, IdPak, KorImeKur) VALUES (?,?,?)");){
            ps.setBigDecimal(1, pricePercentage);
            ps.setInt(2, packageId);
            ps.setString(3, couriersUserName);
            int value=ps.executeUpdate();
            if(value == 1) {
                PreparedStatement ps1 = conn.prepareStatement("SELECT MAX(IdPon) FROM Ponuda");
                ResultSet rs = ps1.executeQuery();
                if(rs.next())
                {
                    return rs.getInt(1);
                }
            }

            } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return -1;
    }

    @Override
    public boolean acceptAnOffer(int offerId) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Ponuda SET Status = 1 WHERE IdPon = ?");){
            ps.setInt(1,offerId);
            int value = ps.executeUpdate();
            if (value == 1) {
                PreparedStatement ps2 = conn.prepareStatement("SELECT IdPak FROM Ponuda WHERE IdPon = ?");
                ps2.setInt(1,offerId);
                ResultSet rs = ps2.executeQuery();
                
                if(rs.next()) {
                    int idPak = rs.getInt(1);
                    PreparedStatement ps3 = conn.prepareStatement("UPDATE Paket SET Status = 1, VremeZahteva = GETDATE() WHERE IdPak = ?");
                    ps3.setInt(1,idPak);
                    value = ps3.executeUpdate();
                    
                    if (value == 1) {
                        return true;
                    }
                }
            }
                        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllOffers() {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT IdPon FROM Ponuda");){
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

    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int packageId) {
        List<Pair<Integer,BigDecimal>> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT IdPon,Procenat FROM Ponuda WHERE IdPak = ?");){
            ps.setInt(1, packageId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                lista.add(new vi160137_Pair(rs.getInt(1),rs.getBigDecimal(2)));
            }
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deletePackage(int packageId) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Paket WHERE IdPak = ?");){
            ps.setInt(1,packageId);
            int value = ps.executeUpdate();
            if (value == 1) {
                return true;
            }
                        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeWeight(int packageId, BigDecimal newWeight) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Paket SET Tezina = ? WHERE IdPak = ?");){
            ps.setBigDecimal(1,newWeight);
            ps.setInt(2, packageId);
            int value = ps.executeUpdate();
            if (value == 1) {
                return true;
            }
                        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeType(int packageId, int newType) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Paket SET Tip = ? WHERE IdPak = ?");){
            ps.setInt(1,newType);
            ps.setInt(2, packageId);
            int value = ps.executeUpdate();
            if (value == 1) {
                return true;
            }
                        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Integer getDeliveryStatus(int packageId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT Status FROM Paket WHERE IdPak = ?");){
            ps.setInt(1,packageId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }       
        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int packageId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT Cena FROM Paket WHERE IdPak = ?");){
            ps.setInt(1,packageId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getBigDecimal(1);
            }       
        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Date getAcceptanceTime(int packageId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT VremeZahteva FROM Paket WHERE IdPak = ?");){
            ps.setInt(1,packageId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getDate(1);
            }       
        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int type) {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT IdPak FROM Paket WHERE Tip = ?");){
            ps.setInt(1, type);
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

    @Override
    public List<Integer> getAllPackages() {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT IdPak FROM Paket");){
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

    @Override
    public List<Integer> getDrive(String courierUsername) {
        List<Integer> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT IdPak FROM Paket WHERE KorIme = ?");){
            ps.setString(1, courierUsername);
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

    @Override
    public int driveNextPackage(String courierUserName) {
        String query="SELECT Status FROM Kurir WHERE KorImeKur = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, courierUserName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int status=rs.getInt("Status");

                if(status==0){          //Kada kurir ne vozi, postavljamo ga da vozi
                    query="UPDATE Paket SET Status = 2 WHERE KorIme = ?";
                    ps=conn.prepareStatement(query);
                    ps.setString(1, courierUserName);
                    if(ps.executeUpdate()==0)
                        return -1;
                    query="UPDATE Kurir SET Status = 1 WHERE KorImeKur = ?";
                    PreparedStatement ps2 = conn.prepareStatement(query);
                    ps2.setString(1, courierUserName);
                    ps2.executeUpdate();
                }

                query="SELECT TOP 1 * FROM Paket WHERE Status = 2 AND KorIme = ? ORDER BY VremeZahteva ASC";
                ps=conn.prepareStatement(query);
                ps.setString(1, courierUserName);
                ps.execute();
                rs=ps.getResultSet();
                if(rs.next()){
                    int ret = rs.getInt("IdPak");
                    azurirajBazu(ret,courierUserName);
                    return ret;
                }
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -2;
    }
    
    private BigDecimal calculatePrice(int districtFrom, int districtTo, int packageType, BigDecimal weight) {
        BigDecimal ret = new BigDecimal(0);
        Connection connection=DB.getInstance().getConnection();
        String selectQuery="SELECT Xkoord, Ykoord FROM Opstina WHERE IdO = ?";
        PreparedStatement ps;
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        try {
            ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, districtFrom);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                x1 = rs.getInt("Xkoord");
                y1 = rs.getInt("Ykoord");
            }
            ps = connection.prepareStatement(selectQuery);
            ps.setInt(1, districtTo);
            ps.execute();
            rs = ps.getResultSet();
            if(rs.next()){
                x2 = rs.getInt("Xkoord");
                y2 = rs.getInt("Ykoord");
            }
            double distanca = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
            int osnovnaCena = 0;
            int cenaPoKg = 0;
            BigDecimal tezina = null;
            switch(packageType){
                case 0:
                    osnovnaCena = 10;
                    ret = (new BigDecimal(osnovnaCena)).multiply(new BigDecimal(distanca));
                    break;
                case 1:
                    osnovnaCena = 25;
                    cenaPoKg = 100;
                    tezina = weight.multiply(new BigDecimal(packageType));
                    tezina = tezina.multiply(new BigDecimal(cenaPoKg));
                    ret = (new BigDecimal(osnovnaCena)).add(tezina);
                    ret = (new BigDecimal(distanca)).multiply(ret);
                    break;
                case 2:
                    osnovnaCena = 75;
                    cenaPoKg = 300;
                    tezina = weight.multiply(new BigDecimal(packageType));
                    tezina = tezina.multiply(new BigDecimal(cenaPoKg));
                    ret = (new BigDecimal(osnovnaCena)).add(tezina);
                    ret = (new BigDecimal(distanca)).multiply(ret);
                    break;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    private BigDecimal getPercentageForPackage(int packageId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT Procenat FROM Ponuda WHERE IdPak = ?");){
            ps.setInt(1,packageId);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getBigDecimal(1);
            }       
        
        } catch (SQLException ex) {
        Logger.getLogger(vi160137_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void azurirajBazu(int idPaket, String courierUserName) {
        String query="SELECT TOP 1 * From Paket WHERE KorIme = ? AND Status = 3 ORDER BY VremeZahteva DESC";
        PreparedStatement ps;
        
        try {
            ps=conn.prepareStatement(query);
            ps.setString(1, courierUserName);
            ps.execute();
            ResultSet rs=ps.getResultSet();
            
            double distancaZaPomeranje = 0;
            if(rs.next()){
                int IdStareOpstine = rs.getInt("IdOpsDo");
                
                query="SELECT TOP 1 * From Paket WHERE KorIme = ? AND Status = 2 ORDER BY VremeZahteva ASC";
                ps=conn.prepareStatement(query);
                ps.setString(1, courierUserName);
                ps.execute();
                rs=ps.getResultSet();
                if(rs.next()){
                    int IdNoveOpstine = rs.getInt("IdOpsOd");
                    if(IdStareOpstine != IdNoveOpstine){
                        query="SELECT Xkoord, Ykoord FROM Opstina WHERE IdO = ? OR IdO = ?";
                        ps=conn.prepareStatement(query);
                        ps.setInt(1, IdStareOpstine);
                        ps.setInt(2, IdNoveOpstine);
                        ps.execute();
                        rs=ps.getResultSet();
                        if(rs.next()){
                            int x1=rs.getInt(1);
                            int y1=rs.getInt(2);

                            if(rs.next()){
                                int x2=rs.getInt(1);
                                int y2=rs.getInt(2);
                                
                                distancaZaPomeranje = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
                            }
                        }
                    }
                }
            }            
            
            query="UPDATE Paket SET Status = 3 WHERE IdPak = ?";
            double rashod;
            int indikator = 0;
            int x1=0, x2=0, y1=0, y2=0;
            ps = conn.prepareStatement(query);
            ps.setInt(1, idPaket);
            if(ps.executeUpdate()==0) return;
            rashod=0;
            
            query="SELECT Count(*) FROM Paket WHERE Status = 2 AND KorIme = ?";
            ps=conn.prepareStatement(query);
            ps.setString(1, courierUserName);
            if(!ps.execute()) return;
            rs=ps.getResultSet();
            if(rs.next()){
                if(rs.getInt(1)==0)
                    indikator=1;
                BigDecimal cena=getPriceOfDelivery(idPaket);
                BigDecimal percentage=getPercentageForPackage(idPaket);
                percentage = percentage.divide(new BigDecimal(100)).add(new BigDecimal(1));
                cena = cena.multiply(percentage);
                
                query="SELECT o.Xkoord, o.Ykoord FROM Opstina o, Paket p WHERE p.IdPak = ? AND (p.IdOpsOd = o.IdO OR p.IdOpsDo = o.IdO)";
                ps=conn.prepareStatement(query);
                ps.setInt(1, idPaket);
                ps.execute();
                rs=ps.getResultSet();
                if(rs.next()){
                    x1=rs.getInt(1);
                    y1=rs.getInt(2);
                    
                    if(rs.next()){
                        x2=rs.getInt(1);
                        y2=rs.getInt(2);
                    }
                }
                double distanca = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
                query="SELECT v.TipGoriva, v.Potrosnja FROM Vozilo v, Kurir k WHERE k.KorImeKur = ? AND k.RegBr = v.RegBr";
                ps=conn.prepareStatement(query);
                ps.setString(1, courierUserName);
                ps.execute();
                rs=ps.getResultSet();
                if(rs.next()){
                    int gorivo=rs.getInt(1);
                    BigDecimal potrosnja=rs.getBigDecimal(2);
                    int cenaG=0;
                    
                    if(gorivo==0) cenaG=15;
                    else if(gorivo==1) cenaG=32;
                    else cenaG=36;
                    rashod=cenaG*distanca*potrosnja.doubleValue() + cenaG*distancaZaPomeranje*potrosnja.doubleValue();
                }
                BigDecimal profit=cena.subtract(new BigDecimal(rashod));
                query="UPDATE Kurir SET BrIspPaketa = BrIspPaketa + 1, TrProfit = TrProfit + ? WHERE KorImeKur = ?";
                ps=conn.prepareStatement(query);
                ps.setBigDecimal(1, profit);
                //ps.setDouble(1, profit.doubleValue()); 
                ps.setString(2, courierUserName);
                ps.executeUpdate();
                if(indikator==1){
                    query="UPDATE Kurir SET Profit = Profit + TrProfit, TrProfit = 0, Status = 0 WHERE KorImeKur = ?";
                    ps=conn.prepareStatement(query);
                    ps.setString(1, courierUserName);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(vi160137_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
