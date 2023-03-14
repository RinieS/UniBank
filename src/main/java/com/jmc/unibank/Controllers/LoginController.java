package com.jmc.unibank.Controllers;

import com.jmc.unibank.Models.Model;
import com.jmc.unibank.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox <AccountType> acct_selector;
    public Label payee_address_lbl;
    public TextField payee_address_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acct_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acct_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acct_selector.valueProperty().addListener(observable ->setAcc_selector() );
        login_btn.setOnAction(actionEvent -> onLogin());
    }


    private void onLogin(){

        Stage stage = (Stage)error_lbl.getScene().getWindow();

        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT){
            //evaluate login credentials
            Model.getInstance().evaluateClientCredentials(payee_address_fld.getText(), password_fld.getText());
            if(Model.getInstance().getClientLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showClientWindow();

                //close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            }
            else{
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Invalid username or password");
            }
        }

        else{
            //evaluate admin login creds
            Model.getInstance().evaluateAdminCredentials(payee_address_fld.getText(), password_fld.getText());
            if(Model.getInstance().getAdminLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showAdminWindow();
                //close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            }
            else{
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Invalid username or password");

            }
        }
    }

    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acct_selector.getValue());
        if(acct_selector.getValue() == AccountType.ADMIN){
            payee_address_lbl.setText("Username:");
        }
        else{
            payee_address_lbl.setText("Payee Address:");
        }
    }
}
