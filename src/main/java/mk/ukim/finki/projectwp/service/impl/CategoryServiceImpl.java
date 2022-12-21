package mk.ukim.finki.projectwp.service.impl;

import mk.ukim.finki.projectwp.model.Category;
import mk.ukim.finki.projectwp.repository.impl.InMemoryCategoryRepository;
import mk.ukim.finki.projectwp.repository.jpa.CategoryRepository;
import mk.ukim.finki.projectwp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        if(name==null || name.isEmpty())
            throw new IllegalArgumentException();

        Category to_create = new Category(name,description);
        categoryRepository.save(to_create);
        return to_create;
    }

    @Override
    public void delete(String name) {
        if(name==null || name.isEmpty())
            throw new IllegalArgumentException();
        categoryRepository.deleteByName(name);

    }

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String text) {
        return categoryRepository.findAllByNameLike(text);
    }
}
