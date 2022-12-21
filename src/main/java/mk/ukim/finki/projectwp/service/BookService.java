package mk.ukim.finki.projectwp.service;

import mk.ukim.finki.projectwp.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> findByName(String name);
    Optional<Book> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId);
    Optional<Book> save(String name, String oldName, Double price, Integer quantity, Long categoryId, Long manufacturerId);

    Page<Book> findPaginated(Pageable pageable);
    List<Book> findAllSortedBy (List<Book> books, String comparingAttribute);
    Page<Book> findPaginatedByCategory(Pageable pageable, String category, String sortingParametar);
    void deleteById(Long id);

}
