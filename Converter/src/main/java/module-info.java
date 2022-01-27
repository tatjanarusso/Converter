module com.example.converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.converter to javafx.fxml;
    exports com.example.converter;
    exports com.example.converter.util;
    opens com.example.converter.util to javafx.fxml;
    opens com.example.converter.util.converter to org.apache.pdfbox;
    exports com.example.converter.util.converter;
}