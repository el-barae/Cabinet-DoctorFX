package cabinetdoctor.vue;

import cabinetdoctor.CabinetDoctorApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RendezVous {

    public RendezVous() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CabinetDoctorApplication.class.getResource("RendezVous.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        Stage stage = new Stage();
        stage.setTitle("RendezVous");
        stage.setScene(scene);
        stage.show();
    }
}