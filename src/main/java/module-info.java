module com.example.fb_streamingplatform {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires com.example.fb_streamingplatform;


    opens com.example.fb_streamingplatform to javafx.fxml;
    exports com.example.fb_streamingplatform;
    exports UI;
    opens UI to javafx.fxml;
}