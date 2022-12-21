package mk.ukim.finki.projectwp.web.controller;

import mk.ukim.finki.projectwp.model.PublishingHouse;
import mk.ukim.finki.projectwp.service.PublishingHouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    public PublishingHouseController(PublishingHouseService publishingHouseService) {
        this.publishingHouseService = publishingHouseService;
    }
    @GetMapping
    public String getManufacturersPage(Model model){
        List<PublishingHouse> publishingHouses = this.publishingHouseService.findAll();
        model.addAttribute("publishers", publishingHouses);
        model.addAttribute("bodyContent", "publishers");
        return "master-template";
    }
}
