package mk.ukim.finki.projectwp.repository.jpa;

import mk.ukim.finki.projectwp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameLike(String text);
    Category findCategoryByName(String name);
    void deleteByName(String name);
}
