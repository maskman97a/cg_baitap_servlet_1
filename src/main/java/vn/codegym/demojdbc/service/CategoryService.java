package vn.codegym.demojdbc.service;

import vn.codegym.demojdbc.dto.SearchCategoryDto;
import vn.codegym.demojdbc.entity.Category;
import vn.codegym.demojdbc.model.CategoryModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CategoryService {
    private final CategoryModel categoryModel;

    public CategoryService() {
        categoryModel = new CategoryModel();

    }

    public void renderCreateCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("btnActionValue", "Create");
        req.setAttribute("labelAction", "Create Category");
        req.setAttribute("currentFunction", "create");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/create_category.jsp");
        requestDispatcher.forward(req, resp);
    }


    public void renderCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = getAllCategory();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/views/category.jsp").forward(req, resp);
    }

    public List<Category> getAllCategory() {
        return categoryModel.getAllCategory();
    }

    public void renderEditCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Category category = categoryModel.getCategoryById(id);
            req.setAttribute("btnActionValue", "Update");
            req.setAttribute("labelAction", "Update Category");
            req.setAttribute("currentFunction", "update");
            req.setAttribute("category", category);
            req.setAttribute("listCategories", categoryModel.getAllCategory());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/create_category.jsp");
            requestDispatcher.forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter("name");
            Category category = new Category();
            category.setName(name);
            categoryModel.createCategory(category);
            resp.sendRedirect(req.getContextPath()+"/category");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categoryModel.updateCategory(category);
            resp.sendRedirect(req.getContextPath()+"/category");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchCategory(HttpServletRequest req, HttpServletResponse resp) {
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
            SearchCategoryDto searchCategoryDto = new SearchCategoryDto();
            searchCategoryDto.setInput(input);
            searchCategoryDto.setSize(size);
            searchCategoryDto.setPage(page);
            List<Category> categoryList = categoryModel.search(searchCategoryDto);
            Integer count = categoryModel.count(searchCategoryDto);
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
            req.setAttribute("categoryList", categoryList);
            req.getRequestDispatcher("/views/category.jsp").forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void renderListCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = categoryModel.getAllCategory();
        Integer totalPage = 5;
        Integer currentPage = 1;
        req.setAttribute("listCategories", categoryList);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("currentPage", currentPage);
        req.getRequestDispatcher("views/category.jsp").forward(req, resp);
    }

    public void deleteCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            categoryModel.deleteCategory(id);
            resp.sendRedirect(req.getContextPath()+"/category");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
