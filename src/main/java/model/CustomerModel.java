package model;

import dto.CustomerDto;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer (CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean updateCutomer (CustomerDto dto);
    boolean deleteCustomer (CustomerDto dto);

    List <CustomerDto> allCustomers () throws SQLException, ClassNotFoundException;
    CustomerDto searchCutomer(String id);

}
