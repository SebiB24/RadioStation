module org.example.radiomodelpractic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;

    opens Domain to javafx.base;
    opens org.example.radiomodelpractic to javafx.fxml;
    exports org.example.radiomodelpractic;
}