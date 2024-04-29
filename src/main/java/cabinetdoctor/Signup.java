package cabinetdoctor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Signup {
    public Signup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CabinetDoctorApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 308, 400);
        Stage stage = new Stage();
        stage.setTitle("Sign Up: Gestion de Cabinet-Doctor");
        stage.setScene(scene);
        stage.show();
    }
}
