package mk.ukim.finki.projectwp.service;

import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Book> listAllBooksInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addBookToShoppingCart(String username, Long productId);

    ShoppingCart deleteBookFromShoppingCart(String username, Long productId);
}
