package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.tm.CustomerTm;

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

    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {

    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        Customer c = new Customer(txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));

        String sql = "INSERT INTO customer VALUES ('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"',"+c.getSalary()+")";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","12345");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
            }
            connection.close();
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
    }

    private void loadCustomerTable() {

        ObservableList <CustomerTm> tmlist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","12345");
            Statement stm = connection.createStatement();
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
                tmlist.add(c);
            }
            connection.close();
            tblCustomer.setItems(tmlist);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }

}