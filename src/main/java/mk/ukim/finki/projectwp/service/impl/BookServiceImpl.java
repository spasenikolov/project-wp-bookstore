package mk.ukim.finki.projectwp.service.impl;

import mk.ukim.finki.projectwp.model.Category;
import mk.ukim.finki.projectwp.model.PublishingHouse;
import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.ShoppingCart;
import mk.ukim.finki.projectwp.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.projectwp.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.projectwp.repository.jpa.CategoryRepository;
import mk.ukim.finki.projectwp.repository.jpa.PublishingHouseRepository;
import mk.ukim.finki.projectwp.repository.jpa.BookRepository;
import mk.ukim.finki.projectwp.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.projectwp.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CategoryRepository categoryRepository;
    private final PublishingHouseRepository publishingHouseRepository;


    public BookServiceImpl(BookRepository bookRepository, ShoppingCartRepository shoppingCartRepository, CategoryRepository categoryRepository, PublishingHouseRepository publishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.categoryRepository = categoryRepository;
        this.publishingHouseRepository = publishingHouseRepository;

    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Page<Book> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> books = this.bookRepository.findAll();
        return getBooks(pageSize, currentPage, startItem, books);
    }

    @Override
    public List<Book> findAllSortedBy(List<Book> books, String comparingAttribute) {
        switch (comparingAttribute) {
            case "name":
                return books.stream().sorted(Comparator.comparing(Book::getName)).collect(Collectors.toList());

            case "price":

                return books.stream().sorted(Comparator.comparing(Book::getPrice)).collect(Collectors.toList());
            default: return books;
        }
    }

    @Override
    public Page<Book> findPaginatedByCategory(Pageable pageable, String category, String sortingParametar) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        Category categoryCasted = categoryRepository.findAllByNameLike(category).stream().findFirst().orElse(new Category(""));
        List<Book> booksByCategory = this.bookRepository.findAll();
        if(!category.equals("")){
            booksByCategory = this.bookRepository.findBooksByCategory(categoryCasted);
        }

        List<Book> books = findAllSortedBy(booksByCategory, sortingParametar);
        return getBooks(pageSize, currentPage, startItem, books);
    }

    private Page<Book> getBooks(int pageSize, int currentPage, int startItem, List<Book> books) {
        List<Book> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        Page<Book> bookPage
                = new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());

        return bookPage;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }


    @Override
    @Transactional
    public Optional<Book> save(String name, Double price, Integer quantity, Long categoryId, Long publishingHouseId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(categoryId));
        PublishingHouse publishingHouse = this.publishingHouseRepository.findById(publishingHouseId)
                .orElseThrow(()->new ManufacturerNotFoundException(publishingHouseId));
        this.bookRepository.deleteByName(name);
        return Optional.of(this.bookRepository.save(new Book(name, price, quantity,category, publishingHouse)));
    }

    @Override
    @Transactional
    public Optional<Book> save(String name, String oldName, Double price, Integer quantity, Long categoryId, Long publishingHouseId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(categoryId));
        PublishingHouse publishingHouse = this.publishingHouseRepository.findById(publishingHouseId)
                .orElseThrow(()->new ManufacturerNotFoundException(publishingHouseId));
        this.bookRepository.deleteByName(oldName);
        return Optional.of(this.bookRepository.save(new Book(name, price, quantity,category, publishingHouse)));
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }


}
