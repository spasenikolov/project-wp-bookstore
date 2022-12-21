package mk.ukim.finki.projectwp.model;

import lombok.Data;
import mk.ukim.finki.projectwp.model.enumerations.ShoppingCartStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateCreated;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;
    @ManyToMany (mappedBy = "shoppingCarts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;
    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    public ShoppingCart( User user) {

        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.status = ShoppingCartStatus.CREATED;
        this.books = new ArrayList<>();
    }

    public ShoppingCart() {

    }
}
