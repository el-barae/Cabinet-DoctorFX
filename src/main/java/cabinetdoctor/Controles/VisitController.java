package cabinetdoctor.Controles;

import cabinetdoctor.Model.Visit;
import cabinetdoctor.Service.VisitService;
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

import static cabinetdoctor.Service.VisitService.getIDV;

public class VisitController extends BDInfo implements Initializable {

    @FXML
    private TextField txtSymptoms;
    @FXML
    private TextField txtDiagnostics;
    @FXML
    private DatePicker txtDeh;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtNote;
    @FXML
    private TextField txtMontant;
    @FXML
    private TextField txtCIN;
    @FXML
    private TableView<Visit> table;
    @FXML
    private TableColumn<Visit, String> idCol;
    @FXML
    private TableColumn<Visit, String> symptomsCol;
    @FXML
    private TableColumn<Visit, String> diagnosticsCol;
    @FXML
    private TableColumn<Visit, String> noteCol;
    @FXML
    private TableColumn<Visit, String> dehCol;
    @FXML
    private TableColumn<Visit, String> typeCol;
    @FXML
    private TableColumn<Visit, String> montantCol;
    @FXML
    private TableColumn<Visit, String> cinCol;


    public void showVisit() throws SQLException {
        PreparedStatement pst = null;
        Connection con = DriverManager.getConnection(url, user, password);
        ObservableList<Visit> visitList = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("SELECT * FROM Visit");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Visit visit = new Visit();
                visit.setId(rs.getInt("id"));
                visit.setSymptoms(rs.getString("symptoms"));
                visit.setDiagnostics(rs.getString("diagnostics"));
                visit.setNote(rs.getString("note"));
                visit.setDeh(rs.getDate("deh").toLocalDate()); // Convert SQL Date to LocalDate
                visit.setType(rs.getString("type"));
                visit.setMontant(rs.getInt("montant"));
                visit.setCin(rs.getString("cin"));
                visitList.add(visit);
            }
            table.setItems(visitList);
            idCol.setCellValueFactory(f -> f.getValue().idProperty());
            symptomsCol.setCellValueFactory(f -> f.getValue().symptomsProperty());
            diagnosticsCol.setCellValueFactory(f -> f.getValue().diagnosticsProperty());
            noteCol.setCellValueFactory(f -> f.getValue().noteProperty());
            dehCol.setCellValueFactory(f -> f.getValue().dehProperty());
            typeCol.setCellValueFactory(f -> f.getValue().typeProperty());
            montantCol.setCellValueFactory(f -> f.getValue().montantProperty());
            cinCol.setCellValueFactory(f -> f.getValue().cinProperty());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void getVisitTableRow() {
        table.setRowFactory(tv -> {
            TableRow<Visit> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = table.getSelectionModel().getSelectedIndex();

                    Visit selectedVisit = table.getItems().get(myIndex);
                    txtSymptoms.setText(selectedVisit.getSymptoms());
                    txtDiagnostics.setText(selectedVisit.getDiagnostics());
                    txtNote.setText(selectedVisit.getNote());
                    txtType.setText(selectedVisit.getType());
                    txtMontant.setText(String.valueOf(selectedVisit.getMontant()));
                    txtCIN.setText(selectedVisit.getCin());
                    txtDeh.setValue(selectedVisit.getDeh());
                }
            });
            return myRow;
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showVisit();
            getVisitTableRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterVisit(ActionEvent event) {
        String symptoms = txtSymptoms.getText();
        String diagnostics = txtDiagnostics.getText();
        String note = txtNote.getText();

        if (txtDeh == null) {
            System.out.println("DatePicker txtDeh is not initialized!");
            return;
        }
        LocalDate deh = txtDeh.getValue();
        String type = txtType.getText();
        int montant = Integer.parseInt(txtMontant.getText());
        String cin = txtCIN.getText();

        try {
            VisitService.ajouterVisite(symptoms, diagnostics, note, Date.valueOf(deh), type, montant, cin);
            int id = getIDV();
            Visit newVisit = new Visit(id, symptoms, diagnostics, note, deh, type, montant, cin);
            table.getItems().add(newVisit);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void exporterVisits(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Visits");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            VisitService.exportVisit(file);
        }
    }

    @FXML
    void modifierVisit(ActionEvent event) {
        Visit selectedVisit = table.getSelectionModel().getSelectedItem();
        if (selectedVisit != null) {
            selectedVisit.setSymptoms(txtSymptoms.getText());
            selectedVisit.setDiagnostics(txtDiagnostics.getText());
            selectedVisit.setNote(txtNote.getText());
            selectedVisit.setDeh(txtDeh.getValue());
            selectedVisit.setType(txtType.getText());
            selectedVisit.setMontant(Integer.parseInt(txtMontant.getText()));
            selectedVisit.setCin(txtCIN.getText());

            try {
                VisitService.modifierVisit(selectedVisit);
                table.refresh();
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Visit updated successfully!");
                infoAlert.show();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error updating visit: " + ex.getMessage());
                errorAlert.show();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING, "No visit selected to modify.");
            warningAlert.show();
        }
    }


    @FXML
    void supprimerVisit(ActionEvent event) {
        Visit selectedVisit = table.getSelectionModel().getSelectedItem();
        if (selectedVisit != null) {
            try {
                VisitService.supprimerVisit(selectedVisit.getId());
                table.getItems().remove(selectedVisit);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
