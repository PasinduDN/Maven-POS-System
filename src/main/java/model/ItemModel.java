package model;

import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    Boolean saveItem (ItemDto dto);
    Boolean updateItem(ItemDto dto);
    Boolean deleteItem(String code);

    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
}
