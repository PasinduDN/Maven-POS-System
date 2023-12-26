package controller;

import db.DBConnection;
import dto.CustomerDto;
import dto.ItemDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class ItemFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDes;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;


    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) tblItem.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        ItemDto dto = new ItemDto(
                txtCode.getText(),
                txtDes.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
                );

        String sql = "INSERT INTO item VALUES ( ?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDesc());
            pstm.setDouble(3,dto.getUnitPrice());
            pstm.setInt(4,dto.getQty());

            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
//                loadCustomerTable();
//                clearfields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
    }

    public void initialize(){

    }
}
