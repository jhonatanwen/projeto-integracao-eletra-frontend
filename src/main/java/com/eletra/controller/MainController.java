package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineDTO;
import com.eletra.dto.ModelDTO;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.eletra.service.CategoryService;
import com.eletra.service.LineService;
import com.eletra.service.ModelService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainController implements Initializable {
    @FXML
    TitledPane tpModels;

    @FXML
    ComboBox<String> cbLines;

    @FXML
    TreeView<String> tvModels;

    LineService lineService;
    CategoryService categoryService;
    ModelService modelService;
    final TreeItem<String> rootCategories = new TreeItem<>("root");
    final Map<String, List<TreeItem<String>>> modelsCache = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAPIServices();
        setTpModels();
        setCbLines();
    }

    void setAPIServices() {
        lineService = new LineService();
        categoryService = new CategoryService();
        modelService = new ModelService();
    }

    void setTpModels() {
        tpModels.setDisable(true);
        tvModels.setShowRoot(false);
        tvModels.setRoot(rootCategories);

        rootCategories.setExpanded(true);

        buildModelsCache();
    }

    void setCbLines() {
        cbLines.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    tpModels.setDisable(false);
                    refreshSelection(newValue);
                });
    }

    void buildModelsCache() {
        for (LineDTO line : lineService.getAllLines()) {
            cbLines.getItems().add(line.getName());

            List<TreeItem<String>> categoryItems = new ArrayList<>();

            for (CategoryDTO category : categoryService.getCategoriesByLineId(line.getId())) {
                TreeItem<String> categoryItem = new TreeItem<>(category.getName());

                for (ModelDTO model : modelService.getModelsByCategoryId(category.getId())) {
                    categoryItem.getChildren().add(new TreeItem<>(model.getName()));
                }

                categoryItems.add(categoryItem);
            }

            modelsCache.put(line.getName(), categoryItems);
        }
    }

    void refreshSelection(String lineName) {
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