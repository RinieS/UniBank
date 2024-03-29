package com.jmc.unibank.Controllers.Admin;

import com.jmc.unibank.Models.Model;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
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
    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(actionEvent -> createClient());
        pAddress_box.selectedProperty().addListener((observableValue, oldValue, newValue) ->{
            if(newValue){
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        } );
        ch_acc_box.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue){
                createCheckingAccountFlag = true;
            }
        });
        sv_acc_box.selectedProperty().addListener((observableValue, oldBalue, newValue) ->{
            if(newValue){
                createSavingsAccountFlag = true;
            }
        } );

    }

    private void createClient(){
        //create ch account
        if (createCheckingAccountFlag){
            createAccount("Checking");
        }

        //create savings acct
        if(createSavingsAccountFlag){
            createAccount("Savings");
        }
        //create client
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String password = password_fld.getText();
        Model.getInstance().getDbDriver().createClient(fName, lName,payeeAddress, password, LocalDate.now());
        err_lbl.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight:bold");
        err_lbl.setText("New client has been successfully added");
        emptyFields();
    }

    private void createAccount(String accountType){
        double balance = Double.parseDouble(ch_balance_fld.getText());
        //generate account number
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999)+1000);
        String acctNumber = firstSection + " "+ lastSection;

        if (accountType.equals("Checking")){
            Model.getInstance().getDbDriver().createCheckingAccount(payeeAddress,acctNumber, 10, balance);
        }
        else{
            Model.getInstance().getDbDriver().createSavingsAccount(payeeAddress,acctNumber, 2000, balance);
        }

    }

    private void onCreatePayeeAddress(){
        if(fName_fld.getText() != null && lName_fld.getText()!= null){
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress(){

        int id = Model.getInstance().getDbDriver().getLastClientsId()+1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));

        return "@"+fChar+lName_fld.getText()+id;

    }

    private void emptyFields(){

        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_box.setSelected(false);
        pAddress_lbl.setText("");
        ch_acc_box.setSelected(false);
        ch_balance_fld.setText("");
        sv_balance_fld.setText("");
        sv_acc_box.setSelected(false);
    }

}
