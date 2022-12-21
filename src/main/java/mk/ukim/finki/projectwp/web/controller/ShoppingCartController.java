package mk.ukim.finki.projectwp.web.controller;

import mk.ukim.finki.projectwp.model.ShoppingCart;
import mk.ukim.finki.projectwp.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true)
            ;


            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("books", this.shoppingCartService.listAllBooksInShoppingCart(shoppingCart.getId()));


        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @PostMapping("/add-book/{id}")
    public String addBookToShoppingCart(@PathVariable Long id, HttpServletRequest req) {
        try {
            String username = req.getRemoteUser();
            ShoppingCart shoppingCart =
                    this.shoppingCartService.addBookToShoppingCart(username, id);


            return "redirect:/shopping-cart";
        }
        catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @DeleteMapping("/delete-book/{id}")
    public String deleteBookFromShoppingCart(@PathVariable Long id, HttpServletRequest req){
        try {
            String username = req.getRemoteUser();
            ShoppingCart shoppingCart =
                    this.shoppingCartService.deleteBookFromShoppingCart(username, id);

            return "redirect:/shopping-cart";
        }
        catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }


}
