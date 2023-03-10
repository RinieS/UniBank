package com.jmc.unibank.Views;

import com.jmc.unibank.Controllers.Admin.AdminController;
import com.jmc.unibank.Controllers.Client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    //Client views

    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;

    private AnchorPane accountsView;

    //admin views

    private AnchorPane createClientView;
    private final StringProperty adminSelectedMenuItem;

    public ViewFactory(){

        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView(){
        if(dashboardView == null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionsView() {

        if (transactionsView == null){
            try{
                transactionsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return transactionsView;
    }

    public AnchorPane getAccountsView() {

        if (accountsView == null){
            try{
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return accountsView;
    }



    public void showClientWindow(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }

/*
*Admin views  sections
*
* */

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {

        if(createClientView == null){
            try{
                createClientView = new FXMLLoader(getClass().getResource("Fxml/Admin/CreateClient.fxml")).load();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return createClientView;
    }

    public void showAdminWindow(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);

        createStage(loader);
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader){


        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Uni Bank");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();


    }
}
