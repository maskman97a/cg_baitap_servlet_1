package vn.codegym.demojdbc.service;

import vn.codegym.demojdbc.dto.SearchBookDto;
import vn.codegym.demojdbc.entity.Book;
import vn.codegym.demojdbc.model.BookModel;
import vn.codegym.demojdbc.model.CategoryModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookModel bookModel;
    private final CategoryModel categoryModel;

    public BookService() {
        this.bookModel = new BookModel();
        this.categoryModel = new CategoryModel();
    }

    public void renderCreateBookPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("btnActionValue", "Create");
        req.setAttribute("labelAction", "Create Book");
        req.setAttribute("currentFunction", "create");

        req.setAttribute("listCategories", categoryModel.getAllCategory());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/create_book.jsp");
        requestDispatcher.forward(req, resp);
    }

    public void renderEditBookPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Book book = bookModel.getBookById(id);
            req.setAttribute("btnActionValue", "Update");
            req.setAttribute("labelAction", "Update Book");
            req.setAttribute("currentFunction", "update");
            req.setAttribute("book", book);
            req.setAttribute("listCategories", categoryModel.getAllCategory());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/create_book.jsp");
            requestDispatcher.forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            int price = Integer.parseInt(req.getParameter("price"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            Book book = new Book();
            book.setName(name);
            book.setDescription(description);
            book.setPrice(price);
            book.setCategoryId(categoryId);
            bookModel.createBook(book);
            resp.sendRedirect("/book");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            int price = Integer.parseInt(req.getParameter("price"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setDescription(description);
            book.setPrice(price);
            book.setCategoryId(categoryId);
            bookModel.updateBook(book);
            resp.sendRedirect("/book");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String input = req.getParameter("input");
            int size = 10;
            int page = 1;
            if (req.getParameter("size") != null) {
                size = Integer.parseInt(req.getParameter("size"));
            }
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            SearchBookDto searchBookDto = new SearchBookDto();
            searchBookDto.setInput(input);
            searchBookDto.setSize(size);
            searchBookDto.setPage(page);
            List<Book> bookList = bookModel.search(searchBookDto);
            Integer count = bookModel.count(searchBookDto);
            req.setAttribute("totalRow", count);
            BigDecimal bCount = new BigDecimal(count);
            BigDecimal bSize = new BigDecimal(size);
            // Thực hiện phép chia và làm tròn lên
            BigDecimal totalPage = bCount.divide(bSize, 0, RoundingMode.CEILING);
            req.setAttribute("totalPage", totalPage);

            int tabSize = 10;
            BigDecimal bTabSize = new BigDecimal(10);
            BigDecimal countTab = totalPage.divide(bTabSize, 0, RoundingMode.CEILING);
            for (int tabIndex = 0; tabIndex < countTab.intValue(); tabIndex++) {
                int startValue = tabIndex * tabSize + 1;
                int endValue = (tabIndex + 1) * tabSize;
                if (page >= startValue && page <= endValue) {
                    if (tabIndex == 0) {
                        req.setAttribute("firstTab", true);
                    }
                    if (tabIndex == countTab.intValue() - 1) {
                        req.setAttribute("lastTab", true);
                    }

                    if (tabIndex == 0) {
                        startValue = 1;
                    }
                    if (tabIndex == countTab.intValue() - 1) {
                        endValue = startValue + totalPage.intValue() % tabSize - 1;
                    }
                    req.setAttribute("beginPage", startValue);
                    req.setAttribute("endPage", endValue);
                    break;
                }
            }
            req.setAttribute("currentPage", page);
            req.setAttribute("listBooks", bookList);
            req.getRequestDispatcher("/views/book.jsp").forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void renderListBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Book> bookList = bookModel.getBooks();
        Integer totalPage = 5;
        Integer currentPage = 1;
        req.setAttribute("listBooks", bookList);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("currentPage", currentPage);
        req.getRequestDispatcher("views/book.jsp").forward(req, resp);
    }

    public void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            bookModel.deleteBook(id);
            resp.sendRedirect("/book");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

