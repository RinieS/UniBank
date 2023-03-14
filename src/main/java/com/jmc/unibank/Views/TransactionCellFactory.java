package com.jmc.unibank.Views;

import com.jmc.unibank.Controllers.Client.TransactionCellController;
import com.jmc.unibank.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TransactionCellFactory extends ListCell<Transaction> {

    @Override
    protected void updateItem (Transaction t, boolean empty){

        super.updateItem(t,empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(t);
            loader.setController(controller);
            setText(null);

            try{
                setGraphic(loader.load());
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
