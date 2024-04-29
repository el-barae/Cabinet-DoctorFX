package cabinetdoctor.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import cabinetdoctor.Controles.BDInfo;
import cabinetdoctor.Model.Visit;

public class VisitService extends BDInfo {

     public static int getIDV() throws SQLException {
        int id = 1;
        Connection con = null;
        Savepoint savepoint1 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint1 = con.setSavepoint("save1");
            Statement stmt = con.createStatement();
            String sql = "SELECT MAX(id) AS last_id FROM Visit;";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                id = rs.getInt("last_id");
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


    public static void ajouterVisite(String symptoms, String diagnostics, String note, Date deh, String type, int montant, String cin) throws SQLException {
        Connection con = null;
        Savepoint savepoint1 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint1 = con.setSavepoint("save1");

            PreparedStatement psmt = con.prepareStatement("INSERT INTO Visit (symptoms, diagnostics, note, deh, type, montant, cin) VALUES (?,?,?,?,?,?,?)");

            psmt.setString(1, symptoms);
            psmt.setString(2, diagnostics);
            psmt.setString(3, note);
            psmt.setDate(4, deh);
            psmt.setString(5, type);
            psmt.setInt(6, montant);
            psmt.setString(7, cin);
            int i = psmt.executeUpdate();

            System.out.println(i + " visite insérée avec succès.");

            con.commit();
            con.close();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint1);
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public static int getID() throws SQLException {
        int id = 1;
        Connection con = null;
        Savepoint savepoint1 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint1 = con.setSavepoint("save1");
            Statement stmt = con.createStatement();
            String sql = "SELECT MAX(id) AS last_id FROM Visit;";  // Query adjusted for Visit table
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                id = rs.getInt("last_id");
                id++;  // Increment to get the next available ID
            }

            con.commit();
            con.close();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint1);  // Rollback to savepoint on error
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return id;
    }

    public static void exportVisit(File file) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement smt = con.createStatement();
            String sql = "SELECT symptoms, diagnostics, note, deh, type, montant, cin FROM Visit WHERE deh >= CURDATE() ORDER BY deh ASC";
            ResultSet res = smt.executeQuery(sql);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            int l1 = 25, l2 = 15, l3 = 10; // Adjusted lengths to fit the content better

            writer.write(String.format("%-" + l1 + "s | %-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l3 + "s | %-" + l3 + "s | %-" + l3 + "s\n",
                    "Symptômes", "Diagnostics", "Note", "Date", "Type", "Montant", "CIN"));
            writer.write("---------------------------------------------------------------------------------------------------------------------------------------------------\n");

            while (res.next()) {
                String symptoms = String.format("%-" + l1 + "s", res.getString("symptoms"));
                String diagnostics = String.format("%-" + l1 + "s", res.getString("diagnostics"));
                String note = String.format("%-" + l1 + "s", res.getString("note"));
                String date = String.format("%-" + l2 + "s", res.getDate("deh").toString());
                String type = String.format("%-" + l3 + "s", res.getString("type"));
                String montant = String.format("%-" + l3 + "s", res.getInt("montant"));
                String cin = String.format("%-" + l3 + "s", res.getString("cin"));

                writer.write(symptoms + " | " + diagnostics + " | " + note + " | " + date + " | " + type + " | " + montant + " | " + cin + "\n");
            }

            writer.close();
            System.out.println("Les données ont été écrites dans le fichier avec succès.");

            con.close();
        } catch (SQLException | IOException e) {
            System.out.println("Erreur lors de l'exportation des données : " + e.getMessage());
        }
    }
    public static void modifierVisit(Visit visit) throws SQLException {
        Connection con = null;
        Savepoint savepoint = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("save");

            String sql = "UPDATE Visit SET symptoms = ?, diagnostics = ?, note = ?, deh = ?, type = ?, montant = ?, cin = ? WHERE id = ?";
            PreparedStatement psmt = con.prepareStatement(sql);

            // Set the PreparedStatement parameters from the Visit object
            psmt.setString(1, visit.getSymptoms());
            psmt.setString(2, visit.getDiagnostics());
            psmt.setString(3, visit.getNote());
            psmt.setDate(4, Date.valueOf(visit.getDeh()));  // Converting LocalDate to java.sql.Date
            psmt.setString(5, visit.getType());
            psmt.setInt(6, visit.getMontant());
            psmt.setString(7, visit.getCin());
            psmt.setInt(8, visit.getId());

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La visite avec l'ID " + visit.getId() + " a été modifiée avec succès.");
            } else {
                System.out.println("Aucune visite avec l'ID " + visit.getId() + " trouvée.");
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint);
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void supprimerVisit(int id) throws SQLException {
        Connection con = null;
        Savepoint savepoint = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("save");

            String sql = "DELETE FROM Visit WHERE id = ?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setInt(1, id);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La visite avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucune visite avec l'ID " + id + " trouvée.");
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

}
