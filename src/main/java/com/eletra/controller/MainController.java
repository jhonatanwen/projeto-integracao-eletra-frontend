package com.eletra.controller;

import com.eletra.models.Types;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.EnumMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainController implements Initializable {
    @FXML
    private TitledPane tpModels;

    @FXML
    private ComboBox<Types> cbLines;

    @FXML
    private TreeView<Types> tvModels;

    private final TreeItem<Types> rootCategories = new TreeItem<>(null);
    private final Map<Types, List<TreeItem<Types>>> modelsCache = new EnumMap<>(Types.class);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tpModels.setDisable(true);

        tvModels.setShowRoot(false);
        tvModels.setRoot(rootCategories);
        rootCategories.setExpanded(true);
        buildModelsCache();

        cbLines.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tpModels.setDisable(newValue == null);
            refreshSelection(newValue);
        });
    }

    private void buildModelsCache() {
        for (Types line : Types.getLines()) {
            cbLines.getItems().add(line);

            List<TreeItem<Types>> categoryItems = new ArrayList<>();

            for (Types category : line.getChildren()) {
                TreeItem<Types> categoryItem = new TreeItem<>(category);

                for (Types model : category.getChildren()) {
                    categoryItem.getChildren().add(new TreeItem<>(model));
                }

                categoryItems.add(categoryItem);
            }

            modelsCache.put(line, categoryItems);
        }
    }

    private void refreshSelection(Types line) {
        List<TreeItem<Types>> items = modelsCache.get(line);
        if (items != null) {
            for (TreeItem<Types> categoryItem : items) {
                categoryItem.setExpanded(false);
            }
            rootCategories.getChildren().setAll(items);
        } else {
            rootCategories.getChildren().clear();
        }
    }
}