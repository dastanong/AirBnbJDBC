package com.example.testjdbc.controllers;

import java.util.List;

import com.example.testjdbc.entities.Property;
import com.example.testjdbc.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class PropertyController {

    @Autowired
    PropertyRepository propertyRepository;

    @GetMapping(value="/property", produces="application/json")
    public List<Property> displayProperties() {
        return propertyRepository.getAllProperties();
    }

    @GetMapping(value="/properties", produces="application/json")
    public List<Property> displayPropertiesByInfo(@RequestParam int numRoom, int price, String address) {
        return propertyRepository.getPropertiesByInfo(numRoom, price, address);
    }

    @GetMapping(path="/bookedProperties", produces="application/json")
    public List<Property> displayBookedProperties(){
        return propertyRepository.getBookedProperties();
    }

    @GetMapping(path="/create_property", produces="application/json")
    public void createProperty(
        @RequestParam("address") String address,
        @RequestParam("numRooms") int numRooms,
        @RequestParam("price") int price,
        @RequestParam("allowSmoking") boolean allowSmoking,
        @RequestParam("maxGuestNum") int maxGuestNum
        ){
        Property property = new Property();
        property.setAddress(address);
        property.setAllowSmoking(allowSmoking);
        property.setMaxGuestNum(maxGuestNum);
        property.setNumRooms(numRooms);
        property.setPrice(price);

        propertyRepository.createProperty(property);
    }

    @GetMapping(path = "/update_property")
    public void updateProperty() {
        propertyRepository.updateProperty("123, test address", "Address updated");
    }

    @GetMapping(path = "/delete_property")
    public void deleteProperty() {
        propertyRepository.deleteProperty(5);
    }

    @PostMapping(path="/properties")
    public Property addProperty(@RequestBody Property property) {
        propertyRepository.addProperty(property);
        return property;
    }
}