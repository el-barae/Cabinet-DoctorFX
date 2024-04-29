package cabinetdoctor.Controles;

import cabinetdoctor.vue.Ordonnance;
import cabinetdoctor.vue.Patient;
import cabinetdoctor.vue.RendezVous;
import cabinetdoctor.vue.Visit;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.io.IOException;

public class OptionsController {
    @FXML
    private Button patientButton;
    @FXML
    private Button visitButton;
    @FXML
    private Button ordonnanceButton;
    @FXML
    private Button rendezvousButton;


    public void patientButtonAction(ActionEvent e) throws IOException {
        new Patient();
    }
    public void visitButtonAction(ActionEvent e) throws IOException {
        new Visit();
    }
    public void ordonnanceButtonAction(ActionEvent e) throws IOException {
        new Ordonnance();
    }
    public void rendezvousButtonAction(ActionEvent e) throws IOException {
        new RendezVous();
    }
}
