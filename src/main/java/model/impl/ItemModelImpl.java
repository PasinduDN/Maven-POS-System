package model.impl;

import db.DBConnection;
import dto.ItemDto;
import model.ItemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public Boolean saveItem(ItemDto dto) {
        return null;
    }

    @Override
    public Boolean updateItem(ItemDto dto) {
        return null;
    }

    @Override
    public Boolean deleteItem(String code) {
        return null;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return new ItemDto(
              resultSet.getString(1),
              resultSet.getString(2),
              resultSet.getDouble(3),
              resultSet.getInt(4)
            );
        }
        return null;
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List <ItemDto> list = new ArrayList<>();
        String sql = "SELECT * FROM item";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new ItemDto(
               resultSet.getString(1),
               resultSet.getString(2),
               resultSet.getDouble(3),
               resultSet.getInt(4)
            ));
        }
        return list;
    }
}
