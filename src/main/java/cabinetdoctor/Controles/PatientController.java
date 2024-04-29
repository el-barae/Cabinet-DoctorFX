package cabinetdoctor.Controles;

import cabinetdoctor.Model.Patient;
import cabinetdoctor.Service.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import java.sql.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static cabinetdoctor.Service.PatientService.ajouterP;

public class PatientController extends BDInfo implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField txtCIN;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtSexe;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtDateNaissance;

    @FXML
    private TextField txtTelephone;

    @FXML
    private TableView<Patient> table;

    @FXML
    private TableColumn<Patient, String> cinCol;

    @FXML
    private TableColumn<Patient, String> nomCol;

    @FXML
    private TableColumn<Patient, String> sexeCol;

    @FXML
    private TableColumn<Patient, String> prenomCol;

    @FXML
    private TableColumn<Patient, String> dateNaissanceCol;

    @FXML
    private TableColumn<Patient, String> telephoneCol;

    @FXML
    private MenuItem idMenuPatientCRUD;


    public void showPatients() throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM Patient");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setCin(rs.getString("cin"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
                patient.setSexe(rs.getString("sexe"));
                patient.setDdn(rs.getString("ddn"));
                patient.setTele(rs.getString("tele"));
                patientList.add(patient);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        table.setItems(patientList);
        cinCol.setCellValueFactory(f -> f.getValue().cinProperty());
        nomCol.setCellValueFactory(f -> f.getValue().nomProperty());
        prenomCol.setCellValueFactory(f -> f.getValue().prenomProperty());
        sexeCol.setCellValueFactory(f -> f.getValue().sexeProperty());
        dateNaissanceCol.setCellValueFactory(f -> f.getValue().ddnProperty());
        telephoneCol.setCellValueFactory(f -> f.getValue().teleProperty());
    }

    public void getPatientTableRow(){
        table.setRowFactory(tv -> {
            TableRow<Patient> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    int myIndex =  table.getSelectionModel().getSelectedIndex();

                    String cin = table.getItems().get(myIndex).getCin();
                    String nom = table.getItems().get(myIndex).getNom();
                    String prenom = table.getItems().get(myIndex).getPrenom();
                    String sexe = table.getItems().get(myIndex).getSexe();
                    String ddn = table.getItems().get(myIndex).getDdn();
                    String tele = table.getItems().get(myIndex).getTele();

                    txtCIN.setText(cin);
                    txtNom.setText(nom);
                    txtPrenom.setText(prenom);
                    txtSexe.setText(sexe);
                    txtDateNaissance.setText(ddn);
                    txtTelephone.setText(tele);
                }
            });
            return myRow;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showPatients();
            getPatientTableRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void ajouterPatient(ActionEvent e) {
        String cin = txtCIN.getText();
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String sexe = txtSexe.getText();
        String ddn = txtDateNaissance.getText();
        String tele = txtTelephone.getText();

        try {
            ajouterP(cin, nom, prenom, sexe, ddn, tele);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion des patients");
            alert.setHeaderText("Ajout de patient");
            alert.setContentText("Le patient a été ajouté avec succès !");
            alert.showAndWait();

            // Rafraîchir la table des patients après l'ajout
            table.getItems().clear();
            table.getItems().addAll(PatientService.getAllPatients());

            // Effacer les champs après l'ajout
            txtCIN.clear();
            txtNom.clear();
            txtPrenom.clear();
            txtSexe.clear();
            txtDateNaissance.clear();
            txtTelephone.clear();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout du patient");
            alert.setContentText("Une erreur s'est produite lors de l'ajout du patient. Veuillez réessayer !");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifierPatient(ActionEvent event) {
        String cin = txtCIN.getText();
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String sexe = txtSexe.getText();
        String ddn = txtDateNaissance.getText();
        String tele = txtTelephone.getText();

        try {
            PatientService.modifierP(cin, nom, prenom, sexe, ddn, tele);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion des patients");
            alert.setHeaderText("Modification de patient");
            alert.setContentText("Le patient a été modifié avec succès !");
            alert.showAndWait();
            table.getItems().clear();
            table.getItems().addAll(PatientService.getAllPatients());

            txtCIN.clear();
            txtNom.clear();
            txtPrenom.clear();
            txtSexe.clear();
            txtDateNaissance.clear();
            txtTelephone.clear();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la modification du patient");
            alert.setContentText("Une erreur s'est produite lors de la modification du patient. Veuillez réessayer !");
            alert.showAndWait();
        }
    }


    @FXML
    private void exporterPatient(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les patients");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                PatientService.exportP(file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportation des patients");
                alert.setHeaderText("Exportation réussie");
                alert.setContentText("Les patients ont été exportés avec succès dans le fichier :\n" + file.getAbsolutePath());
                alert.showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de l'exportation des patients");
                alert.setContentText("Une erreur s'est produite lors de l'exportation des patients. Veuillez réessayer !");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void supprimerPatient(ActionEvent event) {
        String cin = txtCIN.getText();

        if (!cin.isEmpty()) {
            try {
                PatientService.supprimerP(cin);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression de patient");
                alert.setHeaderText("Suppression réussie");
                alert.setContentText("Le patient avec CIN " + cin + " a été supprimé avec succès.");
                alert.showAndWait();

                table.getItems().clear();
                table.getItems().addAll(PatientService.getAllPatients());

                txtCIN.clear();
                txtNom.clear();
                txtPrenom.clear();
                txtSexe.clear();
                txtDateNaissance.clear();
                txtTelephone.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur lors de la suppression du patient");
                alert.setContentText("Une erreur s'est produite lors de la suppression du patient. Veuillez réessayer !");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Champ CIN vide");
            alert.setContentText("Veuillez saisir le CIN du patient que vous souhaitez supprimer !");
            alert.showAndWait();
        }
    }


}
