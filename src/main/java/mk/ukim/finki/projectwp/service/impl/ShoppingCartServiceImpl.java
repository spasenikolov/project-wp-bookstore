package mk.ukim.finki.projectwp.service.impl;

import mk.ukim.finki.projectwp.model.Book;
import mk.ukim.finki.projectwp.model.ShoppingCart;
import mk.ukim.finki.projectwp.model.User;
import mk.ukim.finki.projectwp.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.projectwp.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.projectwp.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.projectwp.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.projectwp.model.exceptions.UserNotFoundException;
import mk.ukim.finki.projectwp.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.projectwp.repository.jpa.UserRepository;
import mk.ukim.finki.projectwp.service.BookService;
import mk.ukim.finki.projectwp.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, BookService bookService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Book> listAllBooksInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);

        return this.shoppingCartRepository.findById(cartId).get().getBooks();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                            ShoppingCart cart = new ShoppingCart(user);
                            return this.shoppingCartRepository.save(cart);
                        }
                );
    }



    @Override
    public ShoppingCart addBookToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Book book = this.bookService.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        if(shoppingCart.getBooks().stream().filter(r->r.getId().equals(productId)).collect(Collectors.toList())
                .size()>0) throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getBooks().add(book);
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        shoppingCarts.add(shoppingCart);
        book.setShoppingCarts(shoppingCarts);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart deleteBookFromShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Book book = this.bookService.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));

        shoppingCart.getBooks().remove(book);
        book.getShoppingCarts().remove(shoppingCart);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
