package sample.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import sample.model.Medicine;

public class MedicineView {

    private final BorderPane root;

    private TextField searchField;
    private TableView<Medicine> medicineTable;

    private Button addButton;
    private Button editButton;
    private Button salesButton;
    private Button searchButton;
    private Button homeButton;
    private Button medicinesButton;
    private Button reportsButton;
    private Button settingsButton;
    private Button logoutButton;

    public MedicineView() {
        root = new BorderPane();
        createLayout();
    }

    private void createLayout() {
        root.setTop(createHeader());
        root.setLeft(createSidebar());
        root.setCenter(createMainContent());
    }

    // ===================== HEADER =====================

    private HBox createHeader() {
        Label logo = new Label("FCI PHARMA CARE");
        Label subtitle = new Label("Pharmacy Management System");

        VBox branding = new VBox(3);
        branding.getChildren().addAll(logo, subtitle);

        Label user = new Label("Mohammed  ● Online");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setSpacing(20);
        header.getChildren().addAll(branding, user);

        HBox.setHgrow(branding, Priority.ALWAYS);

        return header;
    }

    // ===================== SIDEBAR =====================

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);

        Label dashboardTitle = new Label("DASHBOARD");

        homeButton = createMenuButton("🏠  Home");
        medicinesButton = createMenuButton("💊  Medicines");
        salesButton = createMenuButton("🛒  Sales");
        reportsButton = createMenuButton("📊  Reports");

        Label managementTitle = new Label("MANAGEMENT");

        addButton = createMenuButton("➕  Add Medicine");
        editButton = createMenuButton("✏  Edit Medicine");
        settingsButton = createMenuButton("⚙  Settings");

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        logoutButton = createMenuButton("🚪  Logout");

        sidebar.getChildren().addAll(
                dashboardTitle,
                homeButton,
                medicinesButton,
                salesButton,
                reportsButton,
                managementTitle,
                addButton,
                editButton,
                settingsButton,
                spacer,
                logoutButton
        );

        return sidebar;
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(42);
        button.setAlignment(Pos.CENTER_LEFT);
        return button;
    }

    // ===================== MAIN CONTENT =====================

    private VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label title = new Label("Medicine Search");
        Label description = new Label("Search and manage available medicines");

        VBox titleSection = new VBox(5);
        titleSection.getChildren().addAll(title, description);

        HBox searchSection = createSearchSection();
        HBox statistics = createStatistics();

        Label tableTitle = new Label("Available Medicines");

        TableView<Medicine> table = createMedicineTable();

        content.getChildren().addAll(
                titleSection,
                searchSection,
                statistics,
                tableTitle,
                table
        );

        VBox.setVgrow(table, Priority.ALWAYS);

        return content;
    }

    private HBox createSearchSection() {
        searchField = new TextField();
        searchField.setPromptText("Search medicine name...");
        searchField.setPrefHeight(45);

        searchButton = new Button("Search");
        searchButton.setPrefHeight(45);

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(searchField, searchButton);

        HBox.setHgrow(searchField, Priority.ALWAYS);

        return searchBox;
    }

    private HBox createStatistics() {
        HBox cards = new HBox(15);

        VBox totalCard = createCard("Total Medicines", "120");
        VBox availableCard = createCard("Available", "108");
        VBox outOfStockCard = createCard("Out of Stock", "12");

        cards.getChildren().addAll(totalCard, availableCard, outOfStockCard);

        return cards;
    }

    private VBox createCard(String title, String value) {
        Label titleLabel = new Label(title);
        Label valueLabel = new Label(value);

        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(200);
        card.getChildren().addAll(titleLabel, valueLabel);

        return card;
    }

    // ===================== TABLE =====================

    private TableView<Medicine> createMedicineTable() {
        medicineTable = new TableView<>();

        TableColumn<Medicine, String> nameColumn = new TableColumn<>("Medicine");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Medicine, Integer> quantityColumn = new TableColumn<>("Stock");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Medicine, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellFactory(column ->
                new TableCell<Medicine, String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            Medicine medicine =
                                    getTableView().getItems().get(getIndex());

                            int quantity = medicine.getQuantity();

                            if (quantity == 0) {
                                setText("● Out of Stock");
                            } else if (quantity <= 10) {
                                setText("● Low Stock");
                            } else {
                                setText("● Available");
                            }
                        }
                    }
                }
        );

        medicineTable.getColumns().addAll(
                nameColumn,
                priceColumn,
                quantityColumn,
                statusColumn
        );

        nameColumn.setPrefWidth(250);
        priceColumn.setPrefWidth(150);
        quantityColumn.setPrefWidth(150);
        statusColumn.setPrefWidth(180);

        medicineTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        return medicineTable;
    }

    // ===================== GETTERS =====================

    public BorderPane getRoot() { return root; }

    public TextField getSearchField() { return searchField; }

    public TableView<Medicine> getMedicineTable() { return medicineTable; }

    public Button getEditButton() { return editButton; }

    public Button getAddButton() { return addButton; }

    public Button getSalesButton() { return salesButton; }

    public Button getSearchButton() { return searchButton; }

    public Button getHomeButton() { return homeButton; }

    public Button getMedicinesButton() { return medicinesButton; }

    public Button getReportsButton() { return reportsButton; }

    public Button getSettingsButton() { return settingsButton; }

    public Button getLogoutButton() { return logoutButton; }
}