package com.pluralsight.NorthwindTradersAPI.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * Represents a product entity in Northwind Traders.
 */
public class Product {

    private Integer productId;

    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotNull(message = "Category ID must be provided")
    private Integer categoryId;

    @NotNull(message = "Unit price must be provided")
    @PositiveOrZero(message = "Unit price must be zero or positive")
    private Double unitPrice;

    public Product() {
    }

    public Product(Integer productId, String productName, Integer categoryId, Double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Overrides for better logging and collections usage

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(categoryId, product.categoryId) &&
                Objects.equals(unitPrice, product.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, categoryId, unitPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
