package mk.ukim.finki.projectwp.web.servlet;

import mk.ukim.finki.projectwp.service.CategoryService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="thymeleaf-category-servlet", urlPatterns = "/servlet/thymeleaf/category")
public class ThymleafCategoryServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final CategoryService categoryService;

    public ThymleafCategoryServlet(SpringTemplateEngine springTemplateEngine, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        webContext.setVariable("ipAddress", req.getRemoteAddr());
        webContext.setVariable("clientAgent", req.getHeader("User-Agent"));
        webContext.setVariable("categories", this.categoryService.listCategories());

        this.springTemplateEngine.process("categories.html", webContext,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");
        categoryService.create(name,desc);
        resp.sendRedirect("/servlet/thymeleaf/category");

    }
}
