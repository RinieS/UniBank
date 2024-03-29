package com.jmc.unibank.Controllers.Client;

import com.jmc.unibank.Models.Model;
import com.jmc.unibank.Models.Transaction;
import com.jmc.unibank.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_balance;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(actionEvent -> onSendMoney());
        accountSummary();
    }

    private void bindData(){
        user_name.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Today, "+ LocalDate.now());
        checking_balance.textProperty().bind(Model.getInstance().getClient().chAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().chAccountProperty().get().accountNumberProperty());
        savings_bal.textProperty().bind(Model.getInstance().getClient().sAccountProperty().get().balanceProperty().asString());
        savings_acc_num.textProperty().bind(Model.getInstance().getClient().sAccountProperty().get().accountNumberProperty());
    }

    private void initLatestTransactionsList(){

        if(Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions();
        }

    }
    private void onSendMoney(){
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().payeeAddressProperty().get();
        ResultSet rs = Model.getInstance().getDbDriver().searchClient(receiver);

        try{
            if(rs.isBeforeFirst()){
                Model.getInstance().getDbDriver().updateBalance(receiver, amount, "ADD");
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }

        //subtract from sender's savings account
        Model.getInstance().getDbDriver().updateBalance(sender, amount, "SUB");
        //update the savings acct balance
        Model.getInstance().getClient().sAccountProperty().get().setBalance(Model.getInstance().getDbDriver().getSavingsAccountsBalance(sender));
        Model.getInstance().getDbDriver().newTransaction(sender, receiver, amount, message);
        //clear the fields
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");

    }

    //method calculates all debits and credits

    private void accountSummary (){
        double income = 0;
        double expenses = 0;
        if(Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }
        for (Transaction transaction: Model.getInstance().getAllTransactions()){
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())){
                expenses = expenses + transaction.amountProperty().get();

            }else {
                income = income + transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ $"+ income);
        expense_lbl.setText("- $"+ expenses);
    }
}
