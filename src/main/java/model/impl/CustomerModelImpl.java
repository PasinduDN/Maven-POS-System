package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean updateCutomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {
        List <CustomerDto> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return list;
    }

    @Override
    public CustomerDto searchCutomer(String id) {
        return null;
    }
}
