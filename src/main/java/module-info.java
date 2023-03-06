module com.jmc.unibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.jmc.unibank to javafx.fxml;
    exports com.jmc.unibank;
    exports com.jmc.unibank.Controllers;
    exports com.jmc.unibank.Controllers.Admin;
    exports com.jmc.unibank.Controllers.Client;
    exports com.jmc.unibank.Models;
    exports com.jmc.unibank.Views;

}