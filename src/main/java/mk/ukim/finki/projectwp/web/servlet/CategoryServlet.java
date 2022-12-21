package mk.ukim.finki.projectwp.web.servlet;

import mk.ukim.finki.projectwp.model.Category;
import mk.ukim.finki.projectwp.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "category-servlet", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {
     private final CategoryService categoryService;

    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getRemoteAddr();
        String clientAgent = req.getHeader("User-Agent");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html>");
        printWriter.println("<head>");
        printWriter.println("</head>");
        printWriter.println("<body>");
        printWriter.println("<h3>");
        printWriter.println(ipAddress);
        printWriter.println(clientAgent);
        printWriter.println("</h3>");
        printWriter.println("<h3> Category List </h3>");
        printWriter.println("<ul>");
        categoryService.listCategories().forEach(r->printWriter.format("<li>Name: %s Description:%s </li>",r.getName(),r.getDescription()));
        printWriter.println("</ul>");

        printWriter.println("<h3> Add Category </h3>");
        printWriter.println("<form method='POST' action = '/servlet/category'>");
        printWriter.println("<label>Name:</label>");
        printWriter.println("<input for='name' type='text' name='name'/>");
        printWriter.println("<label>Description:</label>");
        printWriter.println("<input for='desc' type='text' name='desc'/>");

        printWriter.println("<input type='submit' value='Submit'/>");
        printWriter.println("</form>");


        printWriter.println("</body>");
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String categoryDescription = req.getParameter("desc");
        categoryService.create(categoryName, categoryDescription);

        resp.sendRedirect("/servlet/category");

    }
}
