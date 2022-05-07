package com.example.javafxbinding;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private TextArea textarea1;
    @FXML
    private TextArea textarea2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.bindBidirectional(textarea1.textProperty(),textarea2.textProperty());
    }
}
