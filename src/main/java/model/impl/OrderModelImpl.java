package model.impl;

import db.DBConnection;
import dto.OrderDto;
import model.CustomerModel;
import model.ItemModel;
import model.OrderDetailsModel;
import model.OrderModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.*;
import java.text.SimpleDateFormat;

public class OrderModelImpl implements OrderModel {
    OrderDetailsModel orderDetailsModel = new OrderDetailsModelImpl();
    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO orders VALUES (?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2, dto.getDate());
            pstm.setString(3, dto.getCustId());
            if (pstm.executeUpdate() > 0) {
                boolean isDetailSave = orderDetailsModel.saveOrderDetails(dto.getList());
                if (isDetailSave) {
                    connection.commit();
                    return true;
                }
            }
        }catch (SQLException | ClassNotFoundException  ex){
            connection.rollback();
            ex.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }
}
