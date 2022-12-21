package mk.ukim.finki.projectwp.repository.impl;

import mk.ukim.finki.projectwp.bootstrap.DataHolder;
import mk.ukim.finki.projectwp.model.PublishingHouse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryManufacturerRepository {
    public List<PublishingHouse> findAll(){
        return DataHolder.publishingHouses;
    }
    public Optional<PublishingHouse> findById(long id){
    return DataHolder.publishingHouses.stream().filter(r->r.getId().equals(id)).findFirst();
    }
}
