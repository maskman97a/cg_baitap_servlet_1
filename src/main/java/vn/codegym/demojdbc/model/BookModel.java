package vn.codegym.demojdbc.model;

import vn.codegym.demojdbc.database.DatabaseConnection;
import vn.codegym.demojdbc.dto.SearchBookDto;
import vn.codegym.demojdbc.entity.Book;
import vn.codegym.demojdbc.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private final Connection con;

    public BookModel() {
        this.con = DatabaseConnection.getConnection();
    }

    public List<Book> getBooks() throws SQLException {
        String sql = "SELECT b.id, b.name, b.description, b.price, b.category_id, c.name " + "   FROM books b " + "       left join categories c on c.id = b.category_id";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setDescription(rs.getString(3));
            book.setPrice(rs.getInt(4));
            book.setCategoryId(rs.getInt(5));
            Category category = new Category();
            category.setName(rs.getString(6));
            book.setCategoryId(book.getCategoryId());
            book.setCategory(category);
            bookList.add(book);
        }
        return bookList;
    }

    public Book getBookById(Integer id) throws SQLException {
        String sql = "SELECT b.id, b.name, b.description, b.price, b.category_id, c.name " + "   FROM books b " + "       left join categories c on c.id = b.category_id" + " Where b.id = ? ";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setDescription(rs.getString(3));
            book.setPrice(rs.getInt(4));
            book.setCategoryId(rs.getInt(5));
            Category category = new Category();
            category.setName(rs.getString(6));
            book.setCategoryId(book.getCategoryId());
            book.setCategory(category);
            return book;
        }
        return null;
    }

    public List<Book> search(SearchBookDto searchBookDto) throws SQLException {
        String sql = "SELECT b.id, b.name, b.description, b.price, b.category_id, c.name category_name" +
                " FROM books b left join categories c on b.category_id = c.id " +
                " WHERE 1=1 ";
        if (searchBookDto.getInput() != null) {
            sql += "   and b.name like ? ";
        }
        sql += " limit ? offset ?";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        int index = 1;
        if (searchBookDto.getInput() != null) {
            preparedStatement.setString(index++, "%" + searchBookDto.getInput() + "%");
        }
        preparedStatement.setInt(index++, searchBookDto.getSize());
        preparedStatement.setInt(index, (searchBookDto.getPage() - 1) * searchBookDto.getSize());
        ResultSet rs = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setDescription(rs.getString(3));
            book.setPrice(rs.getInt(4));
            book.setCategoryId(rs.getInt(5));
            Category category = new Category();
            category.setName(rs.getString(6));
            category.setId(book.getCategoryId());
            book.setCategory(category);
            bookList.add(book);
        }
        return bookList;
    }

    public Integer count(SearchBookDto searchBookDto) throws SQLException {
        String sql = "SELECT count(1) " +
                " FROM books b " +
                " WHERE 1=1";
        if (searchBookDto.getInput() != null) {
            sql += "   and name like ? ";
        }
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        int index = 1;
        if (searchBookDto.getInput() != null) {
            preparedStatement.setString(index, "%" + searchBookDto.getInput() + "%");
        }
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void createBook(Book book) throws SQLException {
        String sql = "INSERT INTO books(name, description, price, category_id) " + "           VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getDescription());
        preparedStatement.setInt(3, book.getPrice());
        preparedStatement.setInt(4, book.getCategoryId());
        preparedStatement.executeUpdate();
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books " +
                "   set name =? , description = ?, price = ?, category_id = ?" +
                "   WHERE id = ? ";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getDescription());
        preparedStatement.setInt(3, book.getPrice());
        preparedStatement.setInt(4, book.getCategoryId());
        preparedStatement.setInt(5, book.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteBook(Integer id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        PreparedStatement preparedStatement = this.con.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
