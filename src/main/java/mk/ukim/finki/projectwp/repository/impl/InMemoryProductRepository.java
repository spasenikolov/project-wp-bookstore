package mk.ukim.finki.projectwp.repository.impl;

import mk.ukim.finki.projectwp.bootstrap.DataHolder;
import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.Category;
import mk.ukim.finki.projectwp.model.PublishingHouse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {
public List<Book> findAll(){
    return DataHolder.books;
}
public Optional<Book> findById(Long id){
    return DataHolder.books
            .stream()
            .filter(r->r.getId().equals(id))
            .findFirst();
}
    public Optional<Book> findByName(String name){
        return DataHolder.books
                .stream()
                .filter(r->r.getName().equals(name))
                .findFirst();
    }
public Optional<Book> save(String name, Double price, Integer quantity, Category category, PublishingHouse publishingHouse){
    DataHolder.books.removeIf(r->r.getName().equals(name));
    Book book = new Book(name, price, quantity, category, publishingHouse);
    DataHolder.books.add(book);
    return Optional.of(book);
}
public void deleteById(Long id){
    DataHolder.books.removeIf(r->r.getId().equals(id));
}
}
