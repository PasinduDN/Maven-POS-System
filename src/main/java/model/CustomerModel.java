package model;

import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer (CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean updateCutomer (CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer (String dto) throws SQLException, ClassNotFoundException;

    List <CustomerDto> allCustomers () throws SQLException, ClassNotFoundException;
    CustomerDto searchCutomer(String id);

}
