package com.example.javafxbinding;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CircleController implements Initializable {
    @FXML private AnchorPane root;
    @FXML private Circle circle;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //circle의 xproperty값을 root의 가로길이 property/2 한 값으로 바인딩하겠다는뜻
        circle.centerXProperty().bind(Bindings.divide(root.widthProperty(),2));
        circle.centerYProperty().bind(Bindings.divide(root.heightProperty(), 2));

    }
}
