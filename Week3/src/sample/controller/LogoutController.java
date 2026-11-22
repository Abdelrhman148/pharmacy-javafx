package sample.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import sample.view.MedicineView;

import java.util.Optional;

public class LogoutController {

    private final MedicineView view;

    public LogoutController(MedicineView view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.getLogoutButton().setOnAction(e -> handleLogout());
    }

    private void handleLogout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be returned to the login screen.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) view.getRoot().getScene().getWindow();
            stage.close();
        }
    }
}