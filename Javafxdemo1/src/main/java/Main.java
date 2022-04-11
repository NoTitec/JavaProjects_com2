import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

//javafx라이프 사이클 launch->기본 생성자->init호출->start->사용
public class Main extends Application {//main은 반드시 application 추상클래스상속
    public Main(){//기본생성자 호출 확인
        System.out.println(Thread.currentThread().getName());
    }
    @Override
    public void init()throws Exception{//ui생성변경코드 절대 있으면 안됨 실행 매개변수 전달 목표
        System.out.println(Thread.currentThread().getName());
        Parameters params=getParameters();//매개변수 객체   gradle 어떻게 매개변수?????? cmd 에서 java --ip="192.0.0.1" --port="5000" 형식의 키,값 매개변수를 gradle은 어떻게 전달하는가???
        List<String> list=params.getRaw();//띄어쓰기로 구분하여 List collection 반환
        Map<String,String> map=params.getNamed();//키와 값을 Map collection 반환
        String ip = map.get("ip");
        String port = map.get("port");
        System.out.println(ip+port);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {//메소드 재정의 ui생성과 실행 Stage는 윈도우 창을 의미 일반적으로는 start만 재정의
        VBox root=new VBox();
        root.setPrefWidth(800);
        root.setPrefHeight(800);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Label label=new Label();
        label.setText("Javafx Youtube Open Test");
        label.setFont(new Font(50));

        WebView webView=new WebView();
        WebEngine webEngine=webView.getEngine();
        webEngine.load("https://youtu.be/vAJxgJlelJI");

        Button button = new Button();
        button.setText("확인");
        button.setOnAction(event -> System.exit(0));

        root.getChildren().add(webView);
        root.getChildren().add(label);
        root.getChildren().add(button);
        Scene scene=new Scene(root);//Scene의 매개변수는 Parent 루트 컨테이너이다
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @Override
    public void stop() throws Exception{//x버튼 누르면 호출
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        launch(args);
    }/*Application의 launch가 start를 호출는 생성자를 호출
        launch실행순간 2개의 스레드 생성 1.JavaFX application Thread(ui생성과 변경,init제외 모든 메소드)와 JavaFX Launcher(오직 init을 실행시킴)
        1이외 ui생성변경시도시 예외 발생
    */
}