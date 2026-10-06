package com.eletra.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainController implements Initializable{
    @FXML
    private TitledPane tpLines;

    @FXML
    private TitledPane tpModels;

    @FXML
    private ComboBox<String> cbLines;

    @FXML
    private TreeView<String> tvModels;

    private TreeItem<String> rootCategories = new TreeItem<>("root");
    private List<TreeItem<String>> allCategories = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tpModels.setDisable(true);

        cbLines.getItems().addAll("Cronos", "Ares");

        tvModels.setShowRoot(false);

        cbLines.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tpModels.setDisable(false);
            refreshSelection(newValue);
        });

        loadModels();
    }

    private void loadModels() {
        rootCategories.setExpanded(true);

        TreeItem<String> catTB = new TreeItem<>("Ares TB");
        catTB.getChildren().addAll(
                new TreeItem<>("ARES 7021"),
                new TreeItem<>("ARES 7031"),
                new TreeItem<>("ARES 7023")
        );

        TreeItem<String> catTHS = new TreeItem<>("Ares THS");
        catTHS.getChildren().addAll(
                new TreeItem<>("ARES 8023 15"),
                new TreeItem<>("ARES 8023 200"),
                new TreeItem<>("ARES 8023 2,5")
        );

        TreeItem<String> catOld = new TreeItem<>("Cronos Old");
        catOld.getChildren().addAll(
                new TreeItem<>("CRONOS 6001-A"),
                new TreeItem<>("CRONOS 6003"),
                new TreeItem<>("CRONOS 7023")
        );

        TreeItem<String> catL = new TreeItem<>("Cronos L");
        catL.getChildren().addAll(
                new TreeItem<>("CRONOS 6021L"),
                new TreeItem<>("CRONOS 7023L")
        );

        TreeItem<String> catNG = new TreeItem<>("Cronos-NG");
        catNG.getChildren().addAll(
                new TreeItem<>("CRONOS 6001-NG"),
                new TreeItem<>("CRONOS 6003-NG"),
                new TreeItem<>("CRONOS 6021-NG"),
                new TreeItem<>("CRONOS 6031-NG"),
                new TreeItem<>("CRONOS 7021-NG"),
                new TreeItem<>("CRONOS 7023-NG")
        );

        allCategories.addAll(Arrays.asList(catTB, catTHS, catOld, catL, catNG));

        tvModels.setRoot(rootCategories);
    }

    private void refreshSelection(String line) {
        if (line == null) {
            rootCategories.getChildren().clear();
            return;
        }

        List<TreeItem<String>> filteredList = allCategories.stream()
                .filter(category -> category.getValue().toLowerCase().contains(line.toLowerCase()))
                .collect(Collectors.toList());

        rootCategories.getChildren().setAll(filteredList);
    }
}
