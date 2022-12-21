package mk.ukim.finki.projectwp.repository.jpa;

import mk.ukim.finki.projectwp.model.PublishingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {
}
