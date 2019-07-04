package com.example.testjdbc.repositories;

import java.util.List;

import com.example.testjdbc.entities.Booking;
import com.example.testjdbc.mappers.BookingRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class BookingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM booking";
        RowMapper<Booking> rowMapper = new BookingRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<Booking> getBookingsByPropertyId(int id){
      String sql = "SELECT * FROM booking WHERE property_id = ?";
      RowMapper<Booking> rowMapper = new BookingRowMapper();
      return this.jdbcTemplate.query(sql, new Object[]{id},rowMapper);
    }

    public void updateBookingTotalPrice(Booking booking, int id) {
      String sql = "UPDATE booking SET totalPrice = ? WHERE id = ?";
      int totalPrice = booking.getTotalPrice();
      this.jdbcTemplate.update(sql, totalPrice, id);

      RowMapper<Booking> rowMapper = new BookingRowMapper();
      String selectBookingInfo = "SELECT * FROM booking WHERE id = ?";
      Booking bookingInfo = this.jdbcTemplate.queryForObject(selectBookingInfo, rowMapper, id);
      booking.setId(bookingInfo.getId());
      booking.setCheckInDate(bookingInfo.getCheckInDate());
      booking.setCheckOutDate(bookingInfo.getCheckOutDate());
      booking.setRemarks(bookingInfo.getRemarks());
      booking.setNumGuest(bookingInfo.getNumGuest());
      booking.setUserId(bookingInfo.getUserId());
      booking.setPropertyId(bookingInfo.getPropertyId());
      
    }
}