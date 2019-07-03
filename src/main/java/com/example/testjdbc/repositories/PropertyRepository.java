package com.example.testjdbc.repositories;

import java.util.List;

import com.example.testjdbc.entities.Property;
import com.example.testjdbc.mappers.PropertyRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PropertyRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PropertyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Property> getAllProperties() {                                                                                                                                   
        String sql = "SELECT * FROM \"property\"";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<Property> getPropertiesByInfo(int numRooms, int price, String address) {
        String sql = "SELECT * from \"property\" where numRooms = ? and price = ? and address like ?";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        String arg = "%" + address + "%";
        return this.jdbcTemplate.query(sql, new Object[]{numRooms, price, arg} ,rowMapper);
    }

    public List<Property> getBookedProperties(){
        String sql = "SELECT DISTINCT property.* FROM property JOIN booking ON booking.property_id = property.id";
        RowMapper<Property> rowMapper = new PropertyRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public void addProperty(String address, int numRooms, int price, Boolean allowSmoking, int maxGuestNum) {
        String sql = "INSERT INTO \"property\" VALUES (?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, address, numRooms, price, allowSmoking, maxGuestNum);
    }

    public void createProperty(Property property){
        String sql = "INSERT INTO property VALUES (?,?,?,?,?)";
        this.jdbcTemplate.update(sql, property.getAddress(), property.getNumRooms(),
        property.getPrice(), property.getAllowSmoking(), property.getMaxGuestNum());

        sql = "SELECT id FROM property WHERE address = ?";
        int id = jdbcTemplate.queryForObject(sql, Integer.class,
        property.getAddress());

        property.setId(id);
    }

    public void updateProperty(String oldAddress, String newAddress) {
        String sql = "UPDATE \"property\" SET address = ? WHERE address = ?";
        this.jdbcTemplate.update(sql, newAddress, oldAddress);
    }

    public void deleteProperty(int id) {
        String sql = "DELETE FROM \"property\" WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    public void addProperty(Property property) {
        String sql = "INSERT INTO \"property\" VALUES (?, ?, ?, ?, ?)";
        String address = property.getAddress();
        int numRooms = property.getNumRooms();
        int price = property.getPrice();
        Boolean allowSmoking = property.getAllowSmoking();
        int maxGuestNum = property.getMaxGuestNum();
        this.jdbcTemplate.update(sql, address, numRooms, price, allowSmoking, maxGuestNum);

        String selectPropertyId = "SELECT id FROM \"property\" WHERE address = ?";
        int propertyId = this.jdbcTemplate.queryForObject(selectPropertyId, Integer.class, property.getAddress());

        property.setId(propertyId);
    }
}