package com.jmc.unibank.Controllers.Admin;

import com.jmc.unibank.Models.Client;
import com.jmc.unibank.Models.Model;
import com.jmc.unibank.Views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView <Client>result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(actionEvent -> onClientSearch());
        deposit_btn.setOnAction(actionEvent -> onDeposit());

    }

    private void onClientSearch(){
        ObservableList<Client> searchResults = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(e -> new ClientCellFactory());
        client = searchResults.get(0);
    }

    private void onDeposit(){
        double amount = Double.parseDouble(amount_fld.getText());
        double newBal = amount + client.sAccountProperty().get().balanceProperty().get();
        if(amount_fld.getText() != null){
            Model.getInstance().getDbDriver().depositSavings(client.payeeAddressProperty().get(), newBal);
        }
        emptyFields();
    }

    private void emptyFields(){
        pAddress_fld.setText("");
        amount_fld.setText("");
    }
}
