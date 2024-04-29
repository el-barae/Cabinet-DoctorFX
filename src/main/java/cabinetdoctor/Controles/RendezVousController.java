package cabinetdoctor.Controles;

import cabinetdoctor.Model.RendezVous;
import cabinetdoctor.Service.RendezVousService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

public class RendezVousController extends BDInfo implements Initializable{

    @FXML
    private TextField txtNote;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtCIN;

    @FXML
    private TextField txtHeure;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteButton1;

    @FXML
    private TableView<RendezVous> table;

    @FXML
    private TableColumn<RendezVous, String> idCol;

    @FXML
    private TableColumn<RendezVous, String> noteCol;

    @FXML
    private TableColumn<RendezVous, String> dateCol;

    @FXML
    private TableColumn<RendezVous, String> heureCol;

    @FXML
    private TableColumn<RendezVous, String> cinCol;

    int ID;

    public void showRendezVous() throws SQLException {
        PreparedStatement pst = null;
        Connection con = DriverManager.getConnection(url, user, password);
        ObservableList<RendezVous> rendezVousList = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT * FROM RendezVous");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RendezVous rv = new RendezVous();
                rv.setId(rs.getInt("id"));
                rv.setNote(rs.getString("note"));
                rv.setDate(rs.getDate("date").toLocalDate());
                rv.setHeure(rs.getString("heure"));
                rv.setCinP(rs.getString("cinP"));
                rendezVousList.add(rv);
            }
            table.setItems(rendezVousList);
            idCol.setCellValueFactory(f -> f.getValue().idProperty());
            noteCol.setCellValueFactory(f -> f.getValue().noteProperty());
            dateCol.setCellValueFactory(f -> f.getValue().dateProperty());
            heureCol.setCellValueFactory(f -> f.getValue().heureProperty());
            cinCol.setCellValueFactory(f -> f.getValue().cinPProperty());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void getTableRow(){
        table.setRowFactory( tv -> {
            TableRow<RendezVous> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    int myIndex =  table.getSelectionModel().getSelectedIndex();

                    ID= Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtNote.setText(table.getItems().get(myIndex).getNote());
                    txtDate.setValue(table.getItems().get(myIndex).getDate());
                    txtHeure.setText(table.getItems().get(myIndex).getHeure());
                    txtCIN.setText(table.getItems().get(myIndex).getCinP());

                }
            });
            return myRow;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showRendezVous();
            getTableRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterRendezVous(ActionEvent event) {
        String note = txtNote.getText();
        LocalDate date = txtDate.getValue();
        String heure = txtHeure.getText();
        String cinP = txtCIN.getText();

        try {
            int id=RendezVousService.getID();
            RendezVousService.ajouterRV(note, Date.valueOf(date), heure, cinP);

            RendezVous rendezVous = new RendezVous(id, note, date, heure, cinP);

            table.getItems().add(rendezVous);

            txtNote.clear();
            txtHeure.clear();
            txtCIN.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Rendez-vous ajouté");
            alert.setContentText("Le rendez-vous a été ajouté avec succès !");
            alert.showAndWait();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout du rendez-vous");
            alert.setContentText("Une erreur s'est produite lors de l'ajout du rendez-vous. Veuillez réessayer !");
            alert.showAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void exporterRendezVous(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les rendez-vous");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            RendezVousService.exportRV(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportation réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les rendez-vous non expirés ont été exportés avec succès dans le fichier :\n" + file.getAbsolutePath());
            alert.showAndWait();
        }
    }


    @FXML
    void modifierRendezVous(ActionEvent event) {
        int id = ID;
        String note = txtNote.getText();
        LocalDate date = txtDate.getValue();
        String heure = txtHeure.getText();
        String cinP = txtCIN.getText();

        try {
            RendezVousService.modifierRV(id, note, Date.valueOf(date), heure, cinP);
            table.getItems().clear();
            showRendezVous();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la modification du rendez-vous avec l'ID " + id + ".");
        }
    }


    @FXML
    void supprimerRendezVous(ActionEvent event) {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            RendezVous selectedRv = table.getItems().get(selectedIndex);
            int id = selectedRv.getId();
            try {
                RendezVousService.supprimerRV(id);
                table.getItems().remove(selectedIndex);
                System.out.println("Le rendez-vous a été supprimé avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la suppression du rendez-vous.");
            }
        } else {
            System.out.println("Aucun rendez-vous sélectionné pour la suppression.");
        }
    }

    @FXML
    void supprimerRvExpires(ActionEvent event) {
        try {
            RendezVousService.supprimerRVExpired();
            System.out.println("Les rendez-vous expirés ont été supprimés avec succès !");
            table.getItems().clear();
            showRendezVous();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression des rendez-vous expirés.");
        }
    }



}
