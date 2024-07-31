package vn.codegym.demojdbc.controller;

import vn.codegym.demojdbc.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "categoryController", urlPatterns = {"/category/*"})
public class CategoryController extends HttpServlet {
    private CategoryService categoryService;

    public void init() {
        this.categoryService = new CategoryService();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            categoryService.searchCategory(req, resp);
        } else {
            String url = req.getPathInfo();
            switch (url) {
                case "/create":
                    // Handle create book page request
                    categoryService.renderCreateCategoryPage(req, resp);
                    return;
                case "/edit":
                    // Handle create book page request
                    categoryService.renderEditCategoryPage(req, resp);
                    break;
                case "/delete":
                    // Handle create book page request
                    categoryService.deleteCategory(req, resp);
                    break;
                case "/search":
                    // Handle create book page request
                    categoryService.searchCategory(req, resp);
                    break;
                default:
                    categoryService.searchCategory(req, resp);
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        switch (req.getPathInfo()) {
            case "/create":
                categoryService.createCategory(req, resp);
                break;
            case "/update":
                categoryService.updateCategory(req, resp);
                break;
        }
    }


}
