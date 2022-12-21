package mk.ukim.finki.projectwp.service.impl;

import mk.ukim.finki.projectwp.model.PublishingHouse;
import mk.ukim.finki.projectwp.repository.jpa.PublishingHouseRepository;
import mk.ukim.finki.projectwp.service.PublishingHouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishingHouseServiceImpl implements PublishingHouseService {
    private final PublishingHouseRepository publishingHouseRepository;

    public PublishingHouseServiceImpl(PublishingHouseRepository publishingHouseRepository) {
        this.publishingHouseRepository = publishingHouseRepository;
    }


    @Override
    public List<PublishingHouse> findAll() {
        return this.publishingHouseRepository.findAll();
    }

    @Override
    public Optional<PublishingHouse> findById(Long id) {
        return this.publishingHouseRepository.findById(id);
    }

    @Override
    public void deleteById (Long id){ this.publishingHouseRepository.deleteById(id);}

    @Override
    public Optional<PublishingHouse> save(String name, String address) {
        return Optional.of(publishingHouseRepository.save(new PublishingHouse(name,address)));
    }
}
