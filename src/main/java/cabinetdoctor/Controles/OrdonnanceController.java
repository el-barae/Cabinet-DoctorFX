package cabinetdoctor.Controles;

import cabinetdoctor.Model.Ordonnance;
import cabinetdoctor.Service.OrdonnanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import static cabinetdoctor.Service.OrdonnanceService.getIDO;
import static java.lang.Integer.parseInt;

public class OrdonnanceController extends BDInfo implements Initializable {

    @FXML
    private TextField txtMedicament;

    @FXML
    private TextField txtTest;

    @FXML
    private TextField txtNote;

    @FXML
    private TextField txtIdV;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button deleteButton;


    @FXML
    private TableView<Ordonnance> table;

    @FXML
    private TableColumn<Ordonnance, String> idCol;

    @FXML
    private TableColumn<Ordonnance, String> idVCol;

    @FXML
    private TableColumn<Ordonnance, String> medicamentCol;

    @FXML
    private TableColumn<Ordonnance, String> testCol;

    @FXML
    private TableColumn<Ordonnance, String> noteCol;

    int ID= OrdonnanceService.getID();
    ObservableList<Ordonnance> ordonnanceList = FXCollections.observableArrayList();

    public OrdonnanceController() throws SQLException {
    }

    public void showOrdonnance() throws SQLException {
        PreparedStatement pst = null;
        Connection con = DriverManager.getConnection(url, user, password);
        ObservableList<Ordonnance> ordonnanceList = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT * FROM Ordonnance");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ordonnance ord = new Ordonnance();
                ord.setId(rs.getInt("id"));
                ord.setIdV(rs.getInt("idV"));
                ord.setMedicament(rs.getString("medicament"));
                ord.setTest(rs.getString("test"));
                ord.setNote(rs.getString("note"));
                ordonnanceList.add(ord);
            }
            table.setItems(ordonnanceList);
            idCol.setCellValueFactory(f -> f.getValue().idProperty());
            idVCol.setCellValueFactory(f -> f.getValue().idVProperty());
            medicamentCol.setCellValueFactory(f -> f.getValue().medicamentProperty());
            testCol.setCellValueFactory(f -> f.getValue().testProperty());
            noteCol.setCellValueFactory(f -> f.getValue().noteProperty());
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (pst != null) {
                pst.close(); // Fermer la déclaration
            }
            if (con != null) {
                con.close(); // Fermer la connexion
            }
        }
    }

    public void getTableRow(){
        table.setRowFactory( tv -> {
            TableRow<Ordonnance> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    int myIndex =  table.getSelectionModel().getSelectedIndex();

                    ID= Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtMedicament.setText(table.getItems().get(myIndex).getMedicament());
                    txtTest.setText(table.getItems().get(myIndex).getTest());
                    txtNote.setText(table.getItems().get(myIndex).getNote());
                    txtIdV.setText(table.getItems().get(myIndex).getSIdV());
                }
            });
            return myRow;
        });
    }



    @FXML
    void ajouterOrdonnance(ActionEvent event) {
        String medicament = txtMedicament.getText();
        String test = txtTest.getText();
        String note = txtNote.getText();
        int idV = parseInt(txtIdV.getText());

        try {
            OrdonnanceService.ajouterOrdonnance(medicament,test, note,idV);;
            int id = OrdonnanceService.getIDO();
            Ordonnance ordonnance = new Ordonnance(id, idV, medicament, test, note);
            table.getItems().add(ordonnance);

            txtMedicament.clear();
            txtTest.clear();
            txtNote.clear();
            txtIdV.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Ordonnance ajoutée");
            alert.setContentText("L'ordonnance a été ajoutée avec succès !");
            alert.showAndWait();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout de l'ordonnance");
            alert.setContentText("Une erreur s'est produite lors de l'ajout de l'ordonnance. Veuillez réessayer !");
            alert.showAndWait();
        }
    }

    @FXML
    void modifierOrdonnance(ActionEvent event) {
        String medicament = txtMedicament.getText();
        String test = txtTest.getText();
        String note = txtNote.getText();
        int idV = parseInt(txtIdV.getText());

        try {
            OrdonnanceService.modifierOrdonnance(ID, idV, medicament, test, note);
            table.getItems().clear();
            showOrdonnance();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification de l'ordonnance avec l'ID " + ID + ".");
        }
    }

    @FXML
    void exporterOrdonnance(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les ordonnances");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            OrdonnanceService.exportOrdonnance(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportation réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les ordonnances ont été exportées avec succès dans le fichier :\n" + file.getAbsolutePath());
            alert.showAndWait();
        }
    }

    @FXML
    void supprimerOrdonnance(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Ordonnance selectedOrdonnance = table.getItems().get(selectedIndex);
            int id = selectedOrdonnance.getId();
            try {
                OrdonnanceService.supprimerOrdonnance(id);
                table.getItems().remove(selectedIndex);
                System.out.println("L'ordonnance a été supprimée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la suppression de l'ordonnance.");
            }
        } else {
            System.out.println("Aucune ordonnance sélectionnée pour la suppression.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showOrdonnance();
            getTableRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
