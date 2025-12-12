package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import java.util.List;
import java.util.Optional;

/**
 * DAO interface for CRUD operations on Category entities.
 */
public interface CategoryDao {

    /**
     * Adds a new category.
     *
     * @param category the category to add
     * @return the created category with its generated ID
     */
    Category add(Category category);

    /**
     * Retrieves all categories.
     *
     * @return list of all categories
     */
    List<Category> getAll();

    /**
     * Finds a category by its ID.
     *
     * @param id the category ID
     * @return an Optional containing the category if found, empty otherwise
     */
    Optional<Category> findById(int id);

    /**
     * Deletes a category by its ID.
     *
     * @param id the category ID
     * @return true if the category was deleted, false if not found
     */
    boolean deleteById(int id);

    /**
     * Updates an existing category identified by ID.
     *
     * @param id       the category ID
     * @param category the category data to update
     * @return true if the update was successful, false if the category was not found
     */
    boolean updateById(int id, Category category);
}
