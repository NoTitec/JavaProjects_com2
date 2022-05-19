module com.example.jarpractice {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.jarpractice to javafx.fxml;
    exports com.example.jarpractice;
}