package mk.ukim.finki.projectwp.repository.jpa;

import mk.ukim.finki.projectwp.model.ShoppingCart;
import mk.ukim.finki.projectwp.model.User;
import mk.ukim.finki.projectwp.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus shoppingCartStatus);

}
