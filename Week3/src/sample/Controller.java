package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import sample.model.Medicine;
import sample.view.MedicineView;

public class Controller {

    private final MedicineView medicineView;
    private final ObservableList<Medicine> masterMedicines;
    private final FilteredList<Medicine> filteredMedicines;

    public Controller(MedicineView medicineView) {
        this(medicineView, FXCollections.observableArrayList());
    }

    public Controller(MedicineView medicineView, ObservableList<Medicine> medicines) {
        this.medicineView = medicineView;
        this.masterMedicines = medicines != null ? medicines : FXCollections.observableArrayList();
        this.filteredMedicines = new FilteredList<>(this.masterMedicines, m -> true);

        initializeView();
        bindEvents();
    }

    private void initializeView() {
        if (medicineView != null && medicineView.getMedicineTable() != null) {
            medicineView.getMedicineTable().setItems(filteredMedicines);
        }
    }

    private void bindEvents() {
        if (medicineView == null) {
            return;
        }

        // البحث عند الضغط على زر Search
        if (medicineView.getSearchButton() != null) {
            medicineView.getSearchButton().setOnAction(e -> applySearchFilter());
        }

        // البحث الفوري أثناء الكتابة في حقل البحث
        if (medicineView.getSearchField() != null) {
            medicineView.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> applySearchFilter());
            medicineView.getSearchField().setOnAction(e -> applySearchFilter());
        }

        // أزرار الـ Sidebar المطلوب تفعيلها
        if (medicineView.getHomeButton() != null) {
            medicineView.getHomeButton().setOnAction(e -> {
                resetSearch();
                showInfo("Home", "You are on the Home / Medicine screen.");
            });
        }

        if (medicineView.getMedicinesButton() != null) {
            medicineView.getMedicinesButton().setOnAction(e -> {
                resetSearch();
                showInfo("Medicines", "Showing all medicines.");
            });
        }

        if (medicineView.getSalesButton() != null) {
            medicineView.getSalesButton().setOnAction(e ->
                showInfo("Sales", "Hook SalesView navigation here.")
            );
        }

        if (medicineView.getReportsButton() != null) {
            medicineView.getReportsButton().setOnAction(e ->
                showInfo("Reports", "Hook Reports view / logic here.")
            );
        }

        if (medicineView.getSettingsButton() != null) {
            medicineView.getSettingsButton().setOnAction(e ->
                showInfo("Settings", "Hook Settings view / logic here.")
            );
        }

        if (medicineView.getLogoutButton() != null) {
            medicineView.getLogoutButton().setOnAction(e ->
                showInfo("Logout", "Hook Logout logic here.")
            );
        }
    }

    private void applySearchFilter() {
        if (medicineView == null || medicineView.getSearchField() == null) {
            return;
        }

        String keyword = medicineView.getSearchField().getText();
        String query = keyword == null ? "" : keyword.trim().toLowerCase();

        filteredMedicines.setPredicate(medicine -> {
            if (query.isEmpty()) {
                return true;
            }
            if (medicine == null || medicine.getName() == null) {
                return false;
            }
            return medicine.getName().toLowerCase().contains(query);
        });
    }

    private void resetSearch() {
        if (medicineView != null && medicineView.getSearchField() != null) {
            medicineView.getSearchField().clear();
        }
        filteredMedicines.setPredicate(m -> true);
    }

    public void setMedicines(ObservableList<Medicine> medicines) {
        masterMedicines.setAll(medicines != null ? medicines : FXCollections.observableArrayList());
        applySearchFilter();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
