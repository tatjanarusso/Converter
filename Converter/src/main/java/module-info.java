module com.example.converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.datatransfer;
    requires java.desktop;
    requires itextpdf;


    opens com.example.converter to javafx.fxml;
    exports com.example.converter;
    exports com.example.converter.util;
    opens com.example.converter.util to javafx.fxml, org.apache.pdfbox, com.itextpdf;
}