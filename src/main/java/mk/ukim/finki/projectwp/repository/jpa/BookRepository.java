package mk.ukim.finki.projectwp.repository.jpa;

import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);

    void deleteByName(String name);

    List<Book> findBooksByCategory(Category category);

    @Override
    <S extends Book> S save(S entity);
}
