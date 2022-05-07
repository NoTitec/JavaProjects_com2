package com.example.javafx_thread;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.lang.management.PlatformLoggingMXBean;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    private boolean stop;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void btnStop(ActionEvent event){
        stop=true;

    }

    public void btnStart(ActionEvent event) {
        stop=false;
        Thread thread=new Thread(){
            @Override
            public void run() {
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                while(!stop){

                    String strTime=sdf.format(new Date());
                    Platform.runLater(()->welcomeText.setText(strTime));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}