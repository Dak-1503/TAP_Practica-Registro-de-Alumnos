module com.ites.li.practicaregistrodealumnos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;

    opens com.ites.li.practicaregistrodealumnos to javafx.fxml;
    exports com.ites.li.practicaregistrodealumnos;
}