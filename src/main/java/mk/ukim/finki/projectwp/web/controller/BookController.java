package mk.ukim.finki.projectwp.web.controller;

import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.Category;
import mk.ukim.finki.projectwp.model.PublishingHouse;
import mk.ukim.finki.projectwp.service.CategoryService;
import mk.ukim.finki.projectwp.service.PublishingHouseService;
import mk.ukim.finki.projectwp.service.BookService;
import mk.ukim.finki.projectwp.service.ShoppingCartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller

public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

    private final PublishingHouseService publishingHouseService;

    public BookController(BookService bookService, CategoryService categoryService, ShoppingCartService shoppingCartService, PublishingHouseService publishingHouseService) {
        this.bookService = bookService;
        this.categoryService = categoryService;

        this.publishingHouseService = publishingHouseService;
    }

    @GetMapping("/books")
    public String getProductPage(@RequestParam(required = false) String error,

                                 Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }


    @GetMapping("/listBooks")
    public String listBooks(
            Model model,
            @RequestParam("page")  Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("category") Optional<String> category,
            @RequestParam("ordering") Optional<String> ordering){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        String cat = category.orElse("");
        String order = ordering.orElse("name");


        Page<Book> bookPage = bookService.findPaginatedByCategory(PageRequest.of(currentPage - 1, pageSize), cat, order);

        List<Book> books = this.bookService.findAll();
        List<Category> categories = this.categoryService.listCategories();

        model.addAttribute("books", books);
        model.addAttribute("listCategories", categories);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("page", currentPage);


        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("bodyContent", "books-with-pagination");
        return "master-template";
    }
    @DeleteMapping("books/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model){

        this.bookService.deleteById(id);

        return "redirect:/books";
    }
    @GetMapping("/books/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model){
        List<Category> categories = this.categoryService.listCategories();
        List<PublishingHouse> publishingHouses = this.publishingHouseService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("publishingHouses", publishingHouses);
        model.addAttribute("bodyContent", "addBook");
        return "master-template";
    }
    @GetMapping("books/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model){
        if(this.bookService.findById(id).isPresent()){
            Book book = this.bookService.findById(id).get();
            List<Category> categories = this.categoryService.listCategories();
            List<PublishingHouse> publishingHouses = this.publishingHouseService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("publishingHouses", publishingHouses);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "addBook");
            return "master-template";
        }
        return "redirect:/books?error=ProductNotFound";
    }
    @PostMapping("/books/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam Long category,
                              @RequestParam Long publishingHouse,
                              @RequestParam Optional<String> oldName
                                ){

        String previousName = oldName.orElse(name);
        this.bookService.save(name, previousName, price, quantity, category, publishingHouse);
        return "redirect:/books";
    }

}
