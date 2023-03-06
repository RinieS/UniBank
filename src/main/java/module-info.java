module com.jmc.unibank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jmc.unibank to javafx.fxml;
    exports com.jmc.unibank;
}