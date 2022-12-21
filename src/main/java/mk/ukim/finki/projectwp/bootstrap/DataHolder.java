package mk.ukim.finki.projectwp.bootstrap;

import mk.ukim.finki.projectwp.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Category> categoryList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();
    public static List<PublishingHouse> publishingHouses = new ArrayList<>();

    public static List<Book> books = new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();

//    @PostConstruct
//    public void init(){
//        this.categoryList.add(new Category("Software","Softw cat"));
//        this.categoryList.add(new Category("Books","Books cat"));
//       this.userList.add(new User("spase.nikolov", "sp123","spase", "nikolov" ));
//       this.userList.add(new User("petko.petkov", "pp123","petko", "petkov" ));
//
//        Manufacturer manufacturer = new Manufacturer("Nike", "NY NY");
//        manufacturers.add(manufacturer);
//        Category category = new Category("Sport", "Sport Category");
//        categoryList.add(category);
//        products.add(new Product("Ball", 231.3, 5, category, manufacturer));
//        products.add(new Product("Ball2", 231.3, 3, category, manufacturer));
//        products.add(new Product("Ball3", 2321.3, 8, category, manufacturer));
//
//    }
}
