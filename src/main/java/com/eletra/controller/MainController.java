package com.eletra.controller;

import com.eletra.model.CategoryEntity;
import com.eletra.model.LineEntity;
import com.eletra.model.ModelEntity;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
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
    private ComboBox<String> cbLines;

    @FXML
    private TreeView<String> tvModels;

    private LineController lineController;
    private CategoryController categoryController;
    private ModelController modelController;
    private final TreeItem<String> rootCategories = new TreeItem<>("root");
    private final Map<String, List<TreeItem<String>>> modelsCache = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDatabaseControllers();
        setTpModels();
        setCbLines();
    }

    private void setDatabaseControllers() {
        lineController = new LineController();
        categoryController = new CategoryController();
        modelController = new ModelController();
    }

    private void setTpModels() {
        tpModels.setDisable(true);
        tvModels.setShowRoot(false);
        tvModels.setRoot(rootCategories);

        rootCategories.setExpanded(true);

        buildModelsCache();
    }

    private void setCbLines() {
        cbLines.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tpModels.setDisable(false);
            refreshSelection(newValue);
        });
    }

    private void buildModelsCache() {
        for (LineEntity line : lineController.getAllLines()) {
            cbLines.getItems().add(line.getName());

            List<TreeItem<String>> categoryItems = new ArrayList<>();

            for (CategoryEntity category : categoryController.getCategoriesByLineId(line.getId())) {
                TreeItem<String> categoryItem = new TreeItem<>(category.getName());

                for (ModelEntity model : modelController.getModelsByCategoryId(category.getId())) {
                    categoryItem.getChildren().add(new TreeItem<>(model.getName()));
                }

                categoryItems.add(categoryItem);
            }

            modelsCache.put(line.getName(), categoryItems);
        }
    }

    private void refreshSelection(String lineName) {
        List<TreeItem<String>> items = modelsCache.get(lineName);
        if (items != null) {
            for (TreeItem<String> item : items) {
                item.setExpanded(false);
            }
            rootCategories.getChildren().setAll(items);
        } else {
            rootCategories.getChildren().clear();
        }
    }
}