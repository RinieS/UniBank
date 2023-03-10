package com.jmc.unibank.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_balance_fld;
    public CheckBox sv_acc_box;
    public TextField sv_balance_fld;
    public Button create_client_btn;
    public Label err_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
