package com.example.fxviewcontrols;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewsController implements Initializable {
    @FXML private ListView<String> listView;
    @FXML private TableView<Phone> tableView;//제네릭이라 어떤타입 쓸지 지정해야함
    @FXML private TableColumn<Phone,String> smartPhoneColumn;//테이블뷰 첫번째 열
    @FXML private TableColumn<Phone,String> imagePhoneColumn;
    @FXML private ImageView imageView;
    @FXML private Button okBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //리스트뷰넣을 observable데이터 세팅
        ObservableList<String> listdata=FXCollections.observableArrayList();
        for(int i=1;i<8;i++){
            listdata.add("갤럭시 s"+i);
        }
        listView.setItems(FXCollections.observableArrayList(listdata));//리스트뷰에 아이템 세팅

        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //선택시 자동실행 갤럭시 3을 누르면 Number newValue에는 숫자 2가 들어옴
                tableView.getSelectionModel().select(newValue.intValue());
                tableView.scrollTo(newValue.intValue());
            }
        });
//----------------------------------
        //TableColumn tcSmartPhone = tableView.getColumns().get(0);
        //이렇게 열을 불러오거나 아니면 열에 id를 줘서 fxml로 불러오기도 가능
        //가져온 열에 cell 공장에 Phone객체 smartPhone 필드명의 Property값 지정
        smartPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("smartPhone"));

        //TableColumn tcImage = tableView.getColumns().get(1);
        imagePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        smartPhoneColumn.setStyle("-fx-alignment: CENTER;");
        imagePhoneColumn.setStyle("-fx-alignment: CENTER;");

        ObservableList<Phone> phonelist=FXCollections.observableArrayList();
        //ObservableList는 제네릭이라 여기 타입 지정 안하면 동작은하나 unsafe오류 발생 가능
        for(int i=1;i<8;i++){
            phonelist.add(new Phone("갤럭시 s"+i,"phone"+i+".png"));
        }
        tableView.setItems(phonelist);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Phone>() {
            @Override
            public void changed(ObservableValue<? extends Phone> observable, Phone oldValue, Phone newValue) {
                //상대경로 이미지 만드는법
                //new Image(getClass().getResource("경로").toString());
                //selectedItem인경우 Phone.getxxxx메소드로 직접 선택행의 정보 가져오기 가능
                if(newValue!=null){//아이템이 선택된때와 해제될때 2번 changed호출되서 해제될때는 null이들어오므로
                    imageView.setImage(new Image("https://www.cae.com/media/images/210614-CAEDSWEB-Images-MultiDomain_Space-Overview-reduced.jpeg"));
                }

            }
        });
//------------------------------------------

    }

    public void handlebtnok(ActionEvent e){
        String item=listView.getSelectionModel().getSelectedItem();
        System.out.println(item);
        Phone phone=tableView.getSelectionModel().getSelectedItem();
        System.out.println(phone.getSmartPhone());
        System.out.println(phone.getImage());
    }
    public void handlebtnback(ActionEvent e){
        Platform.exit();
    }
}