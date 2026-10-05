package com.eletra.controller;

import java.net.URL;
import java.util.ResourceBundle;
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {}
}
