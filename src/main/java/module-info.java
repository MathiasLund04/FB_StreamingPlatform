module com.example.fb_streamingplatform {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fb_streamingplatform to javafx.fxml;
    exports com.example.fb_streamingplatform;
}