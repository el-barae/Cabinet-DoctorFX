package cabinetdoctor.vue;

import cabinetdoctor.CabinetDoctorApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Patient {

    public Patient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CabinetDoctorApplication.class.getResource("Patient.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        Stage stage = new Stage();
        stage.setTitle("Gestion des patients");
        stage.setScene(scene);
        stage.show();
    }
}
