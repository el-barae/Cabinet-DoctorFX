package cabinetdoctor.vue;

import cabinetdoctor.CabinetDoctorApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ordonnance {
    public Ordonnance() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CabinetDoctorApplication.class.getResource("Ordonnance.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        Stage stage = new Stage();
        stage.setTitle("Ordonnance");
        stage.setScene(scene);
        stage.show();
    }
}