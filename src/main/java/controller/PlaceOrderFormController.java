package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PlaceOrderFormController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btncart;

    @FXML
    private JFXComboBox<?> cmbCustId;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<?> tblOrder;

    @FXML
    private Label tblTota;

    @FXML
    private JFXTextField txtCustName;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    void backBtnOnAction(ActionEvent event) {

    }

    @FXML
    void cartBtnOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderBtnOnAction(ActionEvent event) {

    }

}
