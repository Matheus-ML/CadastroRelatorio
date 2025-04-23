module org.example.javafxteste {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafxteste to javafx.fxml;
    exports org.example.javafxteste;
}