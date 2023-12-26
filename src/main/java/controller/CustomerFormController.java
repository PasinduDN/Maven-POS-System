package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import dto.tm.CustomerTm;

import java.io.IOException;
import java.sql.*;

public class CustomerFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnRelod;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colSalry;

    @FXML
    private AnchorPane customerWindow;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void backBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) tblCustomer.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadCustomerTable();
        tblCustomer.refresh();
        clearfields();
    }

    private void clearfields() {
        tblCustomer.refresh();
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtID.setEditable(true);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        Customer c = new Customer(txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));

        String sql = "INSERT INTO customer VALUES ('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"',"+c.getSalary()+")";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearfields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalry.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setdata(newValue);
        });

    }

    private void setdata(CustomerTm newValue) {
        if (newValue != null){
            txtID.setEditable(false);
            txtID.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {

        ObservableList <CustomerTm> tmlist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()){
                Button btn = new Button("Delete");
                CustomerTm c = new CustomerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });
                tmlist.add(c);
            }
            tblCustomer.setItems(tmlist);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {

        String sql = "DELETE FROM customer WHERE id='"+id+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong;");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        Customer c = new Customer(txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));

        String sql = "UPDATE customer SET name='"+c.getName()+"', address='"+c.getAddress()+"', salary="+c.getSalary()+" WHERE id='"+c.getId()+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer "+c.getId()+" Updated!").show();
                loadCustomerTable();
                clearfields();
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}