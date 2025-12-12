package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product add(Product product);

    List<Product> getAll();

    Optional<Product> findById(int id);

    boolean updateById(int id, Product product);

    boolean deleteById(int id);

}