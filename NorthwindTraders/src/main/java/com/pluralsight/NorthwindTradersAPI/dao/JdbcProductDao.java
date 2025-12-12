package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryID"));
                product.setUnitPrice(rs.getDouble("UnitPrice"));
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products", e);
        }

        return products;
    }

    @Override
    public Optional<Product> findById(int id) {
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM Products WHERE ProductID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setCategoryId(rs.getInt("CategoryID"));
                    product.setUnitPrice(rs.getDouble("UnitPrice"));
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding product by ID " + id, e);
        }
    }

    @Override
    public Product add(Product product) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getProductName());
            if (product.getCategoryId() == null || product.getCategoryId() == 0) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, product.getCategoryId());
            }

            if (product.getUnitPrice() == null) {
                stmt.setNull(3, Types.DOUBLE);
            } else {
                stmt.setDouble(3, product.getUnitPrice());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adding product", e);
        }

        return product;
    }

    @Override
    public boolean updateById(int id, Product product) {
        String sql = """
                UPDATE Products
                SET ProductName = COALESCE(?, ProductName),
                    CategoryID = COALESCE(?, CategoryID),
                    UnitPrice = COALESCE(?, UnitPrice)
                WHERE ProductID = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());

            if (product.getCategoryId() == null || product.getCategoryId() == 0) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, product.getCategoryId());
            }

            if (product.getUnitPrice() == null) {
                stmt.setNull(3, Types.DOUBLE);
            } else {
                stmt.setDouble(3, product.getUnitPrice());
            }

            stmt.setInt(4, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product with ID " + id, e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product with ID " + id, e);
        }
    }
}
