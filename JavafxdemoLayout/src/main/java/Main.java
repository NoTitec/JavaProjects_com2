import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;

//javafx라이프 사이클 launch->기본 생성자->init호출->start->사용
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        HBox hbox=new HBox();
//        hbox.setPadding(new Insets(50,10,10,50));//안쪽여백설정
//        hbox.setSpacing(10);//컨트롤간 수평간격
//
//        TextField textField = new TextField();
//        textField.setPrefWidth(200);//창 크기
//        Button button = new Button();
//        HBox.setMargin(button,new Insets(10,10,50,50));//마진설정
//        button.setText("ok");
//
//        ObservableList list=hbox.getChildren();// hbox의 observablelist 얻기
//        list.add(textField);
//        list.add(button);
//        Scene scene = new Scene(hbox);
//
//        primaryStage.setTitle("main");
//        primaryStage.setScene(scene);//루트 컨테이너 넣기
//        primaryStage.show();
//---------------------------------------------- fxml로 scene 만들기
        Parent parent = FXMLLoader.load(getClass().getResource("root.fxml"));
        Scene scene1 = new Scene(parent);
        primaryStage.setTitle("main");
        primaryStage.setScene(scene1);//루트 컨테이너 넣기
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}


//패딩은 안쪽여백 마진은 바깥쪽 여백 여백은 Insets객체생서해서 설정
//여백은 컨테이너가 가지고있음따라서 패딩설정시엔 Insets설정만 마진은 대상이되는 컨트롤객체를 첫번째인자로주고 2번째인자로 Insets설정
//fxml에서는 패딩은 컨테이너에서 태그로 마진은 컨테이너 의 컨트롤안에서 컨테이너.margin태그로 설정