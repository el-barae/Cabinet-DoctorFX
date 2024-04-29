/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package cabinetdoctor.Service;

import cabinetdoctor.Controles.BDInfo;
import cabinetdoctor.Model.RendezVous;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author el-barae
 */
public class RendezVousService extends BDInfo {
    
    public static void ajouterRV(String note, Date date, String heure, String cinP) throws SQLException, InterruptedException {
    Connection con = null;
    Savepoint savepoint1 = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint1 = con.setSavepoint("save1");

        PreparedStatement psmt = con.prepareStatement("INSERT INTO RendezVous (note, date, heure, cinP) VALUES (?,?,?,?)");

        psmt.setString(1, note);
        psmt.setDate(2, (java.sql.Date) date);
        psmt.setString(3, heure);
        psmt.setString(4, cinP);
        int i = psmt.executeUpdate();

        System.out.println(i + "Le rendez-vous inséré avec succès.");

        con.commit();
        con.close();
    } catch (SQLException e) {
        if (con != null) {
            con.rollback(savepoint1);
        }
        e.printStackTrace();
    }
}

    public static ObservableList<RendezVous> getAllRendezVous() throws SQLException {
        ObservableList<RendezVous> rendezVousList = FXCollections.observableArrayList();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = DriverManager.getConnection(url, user, password);

            statement = con.prepareStatement("SELECT * FROM RendezVous");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String note = resultSet.getString("note");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String heure = resultSet.getString("heure");
                String cinP = resultSet.getString("cinP");

                RendezVous rendezVous = new RendezVous(id, note, date, heure, cinP);
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }

        // Retournez la liste des rendez-vous récupérés
        return rendezVousList;
    }
    
    public static int getID() throws SQLException{
        int id = 1;
        Connection con = null;
    Savepoint savepoint1 = null;
        try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint1 = con.setSavepoint("save1");
        Statement stmt = con.createStatement();
        String sql = "SELECT MAX(id) AS last_id FROM RendezVous;";
        ResultSet rs = stmt.executeQuery(sql);
    
        if (rs.next()) {
            id = rs.getInt("last_id");
            id++;
        }

        con.commit();
        con.close();
    } catch (SQLException e) {
        if (con != null) {
            con.rollback(savepoint1);
        }
        e.printStackTrace();
    }
        return id;
    }

    public static void exportRV(File file) {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT note, date, heure, cinP FROM RendezVous WHERE date >= CURDATE() ORDER BY date ASC, heure ASC";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int l1 = 25, l2 = 15; 

        writer.write(String.format("%-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l2 + "s\n", "Note", "Date", "Heure", "CIN"));
        writer.write("----------------------------------------------------------------------------------------\n");

        while (res.next()) {
            String note = String.format("%-" + l1 + "s", res.getString("note"));
            String date = String.format("%-" + l1 + "s", res.getString("date"));
            String heure = String.format("%-" + l2 + "s", res.getString("heure"));
            String cinP = String.format("%-" + l2 + "s", res.getString("cinP"));

            System.out.println(note + " | " + date + " | " + heure + " | " + cinP);

            writer.write(note + " | " + date + " | " + heure + " | " + cinP + "\n");
        }

        writer.close();
        System.out.println("Les données ont été écrites dans le fichier avec succès.");

        con.close();
    } catch (SQLException | IOException e) {
        System.out.println(e);
    }
}
    
    
    public static void modifierRV(int id, String note, Date date, String heure, String cinP) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "UPDATE RendezVous SET note = ?, date = ?, heure = ?, cinP = ? WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, note);
        psmt.setDate(2, (java.sql.Date) date);
        psmt.setString(3, heure);
        psmt.setString(4, cinP);
        psmt.setInt(5, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Le rendez-vous avec l'ID " + id + " a été modifié avec succès.");
        } else {
            System.out.println("Aucun rendez-vous avec l'ID " + id + " trouvé.");
        }

        con.commit();
        con.close();
    } catch (SQLException e) {
        if (con != null) {
            con.rollback(savepoint);
            con.close();
        }
        e.printStackTrace();
    }
}

    
    public static void supprimerRV(int id) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "DELETE FROM RendezVous WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setInt(1, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Le rendez-vous avec l'ID " + id + " a été supprimé avec succès.");
        } else {
            System.out.println("Aucun rendez-vous avec l'ID " + id + " trouvé.");
        }

        con.commit();
        con.close();
    } catch (SQLException e) {
        if (con != null) {
            con.rollback(savepoint);
            con.close();
        }
        e.printStackTrace();
    }
}
    
    public static void supprimerRVExpired() throws SQLException {
        Connection cn = null;
        Savepoint savepoint = null;
    try {
    	cn = DriverManager.getConnection(url, user, password);
        cn.setAutoCommit(false);
        savepoint = cn.setSavepoint("save");
    	Statement smt = cn.createStatement();
        String deleteQuery = "DELETE FROM RendezVous WHERE date < CURDATE();";
        int rowsAffected = smt.executeUpdate(deleteQuery);
        cn.commit();
        cn.close();
    } catch (SQLException e) {
        if (cn != null) {
            cn.rollback(savepoint);
            cn.close();
        }
        e.printStackTrace();
    }
}
    
}
