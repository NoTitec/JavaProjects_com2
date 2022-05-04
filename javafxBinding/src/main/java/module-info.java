module com.example.javafxbinding {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxbinding to javafx.fxml;
    exports com.example.javafxbinding;
}