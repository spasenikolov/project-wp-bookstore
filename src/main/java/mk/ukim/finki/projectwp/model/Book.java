package mk.ukim.finki.projectwp.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class Book implements Comparable<Book>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

   private Long id;
   private String name;
   private Double price;
   private Integer quantity;


   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Category category;
   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   private PublishingHouse publishingHouse;

   @ManyToMany
   private List<ShoppingCart> shoppingCarts;


    public Book(String name, Double price, Integer quantity, Category category, PublishingHouse publishingHouse) {

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.publishingHouse = publishingHouse;



    }

    public Book() {
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    @Override
    public int compareTo(Book o) {
        return this.getName().compareTo(o.getName());
    }

}
