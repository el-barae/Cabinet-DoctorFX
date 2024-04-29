package cabinetdoctor.vue;

import cabinetdoctor.CabinetDoctorApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;




public class Visit {
    public Visit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CabinetDoctorApplication.class.getResource("Visit.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        Stage stage = new Stage();
        stage.setTitle("Gestion des Visites");
        stage.setScene(scene);
        stage.show();
    }
}