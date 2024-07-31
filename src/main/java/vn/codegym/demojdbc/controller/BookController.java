package vn.codegym.demojdbc.controller;

import vn.codegym.demojdbc.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "bookServlet", urlPatterns = {"/book/*"})
public class BookController extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() {
        this.bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            bookService.searchBook(req, resp);
        } else {
            String url = req.getPathInfo();
            switch (url) {
                case "/create":
                    // Handle create book page request
                    bookService.renderCreateBookPage(req, resp);
                    return;
                case "/edit":
                    // Handle create book page request
                    bookService.renderEditBookPage(req, resp);
                    break;
                case "/delete":
                    // Handle create book page request
                    bookService.deleteBook(req, resp);
                    break;
                case "/search":
                    // Handle create book page request
                    bookService.searchBook(req, resp);
                    break;
                default:
                    bookService.searchBook(req, resp);
            }
        }


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        switch (req.getPathInfo()) {
            case "/create":
                // Handle create book request
                bookService.createBook(req, resp);
                break;
            case "/update":
                bookService.updateBook(req, resp);
                break;
        }
    }


}
