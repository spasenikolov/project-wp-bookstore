package mk.ukim.finki.projectwp.service;

import mk.ukim.finki.projectwp.model.PublishingHouse;

import java.util.List;
import java.util.Optional;

public interface PublishingHouseService {
    List<PublishingHouse> findAll();
    Optional<PublishingHouse> findById(Long id);

    void deleteById(Long id);

    Optional<PublishingHouse> save (String name, String address);

}
