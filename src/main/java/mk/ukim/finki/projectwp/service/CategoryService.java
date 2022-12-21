package mk.ukim.finki.projectwp.service;

import mk.ukim.finki.projectwp.model.Category;

import java.util.List;

public interface CategoryService {
    Category create (String name, String description);

    void delete(String name);
    List<Category> listCategories();
    List<Category> searchCategories(String serachText);
}
