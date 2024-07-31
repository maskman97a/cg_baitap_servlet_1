package vn.codegym.demojdbc.model;

import vn.codegym.demojdbc.database.DatabaseConnection;
import vn.codegym.demojdbc.dto.SearchCategoryDto;
import vn.codegym.demojdbc.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    Connection connection;

    List<Category> categoryList;

    public CategoryModel() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public Category getCategoryById(Integer id) throws SQLException {
        String sql = "SELECT id, name " + "   FROM CATEGORIES " +
                " Where id = ? ";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt(1));
            category.setName(rs.getString(2));
            return category;
        }
        return null;
    }

    public List<Category> search(SearchCategoryDto searchCategoryDto) throws SQLException {
        String sql = "SELECT id, name " + " " +
                "FROM categories " +
                " where 1=1 ";
        if (searchCategoryDto.getInput() != null) {
            sql += "   and name like ? ";
        }
        sql += " limit ? offset ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        int index = 1;
        if (searchCategoryDto.getInput() != null) {
            preparedStatement.setString(index++, "%" + searchCategoryDto.getInput() + "%");
        }
        preparedStatement.setInt(index++, searchCategoryDto.getSize());
        preparedStatement.setInt(index++, (searchCategoryDto.getPage() - 1) * searchCategoryDto.getSize());
        ResultSet rs = preparedStatement.executeQuery();
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt(1));
            category.setName(rs.getString(2));
            categories.add(category);
        }
        return categories;
    }

    public Integer count(SearchCategoryDto searchCategoryDto) throws SQLException {
        String sql = "SELECT count(1) " +
                " FROM categories b " +
                " WHERE 1=1";
        if (searchCategoryDto.getInput() != null) {
            sql += "   and name like ? ";
        }
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        int index = 1;
        if (searchCategoryDto.getInput() != null) {
            preparedStatement.setString(index, "%" + searchCategoryDto.getInput() + "%");
        }
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO CATEGORIES(name) " + "           VALUES (?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, category.getName());
        return preparedStatement.executeUpdate();
    }

    public int updateCategory(Category category) throws SQLException {
        String sql = "UPDATE categories " +
                "   set name =? " +
                "   WHERE id = ? ";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setInt(2, category.getId());
        return preparedStatement.executeUpdate();
    }

    public int deleteCategory(Integer id) throws SQLException {
        String sql = "DELETE FROM CATEGORIES WHERE id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }
}
