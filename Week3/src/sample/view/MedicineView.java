package sample.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sample.model.Medicine;

import java.util.List;

public class ReportsView {

    private final VBox view;
    private final TableView<Medicine> lowStockTable;
    private final TableView<SaleRecord> salesTable;
    private final ObservableList<SaleRecord> salesList;
    private final Label totalSalesLabel;
    private final Label lowStockCountLabel;
    private final Button backToMedicinesButton;

    public ReportsView(List<Medicine> medicines) {
        view = new VBox(20);
        view.setPadding(new Insets(30));

        salesList = FXCollections.observableArrayList();
        lowStockTable = new TableView<>();
        salesTable = new TableView<>();
        totalSalesLabel = new Label("Total Sales: 0.00");
        lowStockCountLabel = new Label("Low / Out of Stock: 0");
        backToMedicinesButton = new Button("← Back to Medicines");

        buildUI();
        refreshReports(medicines);
    }

    private void buildUI() {
        Label title = new Label("Reports");
        Label subtitle = new Label("Sales report and low/out-of-stock medicines");

        HBox topBar = new HBox(15);
        topBar.getChildren().addAll(backToMedicinesButton, totalSalesLabel, lowStockCountLabel);

        // =========================
        // Low Stock Medicines Table
        // =========================
        Label lowStockTitle = new Label("Low & Out of Stock Medicines (Stock <= 10)");

        TableColumn<Medicine, String> medNameCol = new TableColumn<>("Medicine");
        medNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, Double> medPriceCol = new TableColumn<>("Price");
        medPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Medicine, Integer> medQtyCol = new TableColumn<>("Stock");
        medQtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        lowStockTable.getColumns().addAll(medNameCol, medPriceCol, medQtyCol);
        lowStockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        lowStockTable.setPrefHeight(200);

        // =========================
        // Sales Report Table
        // =========================
        Label salesTitle = new Label("Sales Report");

        TableColumn<SaleRecord, String> saleMedCol = new TableColumn<>("Medicine");
        saleMedCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMedicineName()));

        TableColumn<SaleRecord, Integer> saleQtyCol = new TableColumn<>("Sold Qty");
        saleQtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<SaleRecord, Double> saleTotalCol = new TableColumn<>("Total");
        saleTotalCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotal()).asObject());

        salesTable.setItems(salesList);
        salesTable.getColumns().addAll(saleMedCol, saleQtyCol, saleTotalCol);
        salesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        salesTable.setPrefHeight(200);

        VBox.setVgrow(lowStockTable, Priority.ALWAYS);
        VBox.setVgrow(salesTable, Priority.ALWAYS);

        view.getChildren().addAll(
                title,
                subtitle,
                topBar,
                lowStockTitle,
                lowStockTable,
                salesTitle,
                salesTable
        );
    }

    public void refreshReports(List<Medicine> medicines) {
        ObservableList<Medicine> lowStockItems = FXCollections.observableArrayList();
        if (medicines != null) {
            for (Medicine med : medicines) {
                if (med.getQuantity() <= 10) {
                    lowStockItems.add(med);
                }
            }
        }
        lowStockTable.setItems(lowStockItems);
        lowStockCountLabel.setText("Low / Out of Stock: " + lowStockItems.size());

        double totalSales = 0.0;
        for (SaleRecord sale : salesList) {
            totalSales += sale.getTotal();
        }
        totalSalesLabel.setText(String.format("Total Sales: %.2f", totalSales));
    }

    public void addSale(String medicineName, int quantity, double unitPrice) {
        salesList.add(new SaleRecord(medicineName, quantity, quantity * unitPrice));
        double totalSales = 0.0;
        for (SaleRecord sale : salesList) {
            totalSales += sale.getTotal();
        }
        totalSalesLabel.setText(String.format("Total Sales: %.2f", totalSales));
    }

    public VBox getView() {
        return view;
    }

    public Button getBackToMedicinesButton() {
        return backToMedicinesButton;
    }

    public static class SaleRecord {
        private final String medicineName;
        private final int quantity;
        private final double total;

        public SaleRecord(String medicineName, int quantity, double total) {
            this.medicineName = medicineName;
            this.quantity = quantity;
            this.total = total;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotal() {
            return total;
        }
    }
}
