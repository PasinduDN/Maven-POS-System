package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.CustomerTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import javax.sql.rowset.Predicate;
import java.io.IOException;
import java.sql.*;

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
//    import java.util.function.Predicate;



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
                loadItemTable();
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
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadItemTable();

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterItems(newValue);
        });

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setdata(newValue);
        });

//        txtSearch.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
//                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
//                    @Override
//                    public boolean test(TreeItem<ItemTm> treeItem) {
//                        return treeItem.getValue().getCode().contains(newValue) ||
//                                treeItem.getValue().getDesc().contains(newValue);
//                    }
//                });
//            }
//        });

    }

    private void filterItems(String newValue) {
        ObservableList<ItemTm> originalList = tblItem.getItems();
        FilteredList<ItemTm> filteredList = new FilteredList<>(originalList);

        filteredList.setPredicate(item ->
                item.getCode().contains(newValue) || item.getDesc().contains(newValue));

        tblItem.setItems(filteredList);
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmlist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()){
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                ItemTm c = new ItemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    DeleteItem(c.getCode());
                });
                tmlist.add(c);
            }

            tblItem.setItems(tmlist);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void DeleteItem(String code) {

        String sql = "DELETE FROM item WHERE code='"+code+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong;");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setdata(ItemTm newValue) {
        if (newValue != null){
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDes.setText(newValue.getDesc());
            txtPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }
}
