package com.example.javafxbinding;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Slider slider;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //컨트롤의 타입 프로퍼티에 속성변경감지 리스너 등록
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override//속성변경시 changed메소드 자동호출
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                welcomeText.setFont(new Font(newValue.doubleValue()));
            }
        });
    }
}