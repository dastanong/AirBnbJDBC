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

      String selectBookingId = "SELECT id FROM booking WHERE id = ?";
      int bookingId = this.jdbcTemplate.queryForObject(selectBookingId, Integer.class, id);
      booking.setId(bookingId);

      String selectCheckInDate = "SELECT checkInDate FROM booking WHERE id = ?";
      String checkInDate = this.jdbcTemplate.queryForObject(selectCheckInDate, String.class, id);
      booking.setCheckInDate(checkInDate);

      String selectCheckOutDate = "SELECT checkOutDate FROM booking WHERE id = ?";
      String checkOutDate = this.jdbcTemplate.queryForObject(selectCheckOutDate, String.class, id);
      booking.setCheckOutDate(checkOutDate);

      String selectRemarks = "SELECT remarks FROM booking WHERE id = ?";
      String remark = this.jdbcTemplate.queryForObject(selectRemarks, String.class, id);
      booking.setRemarks(remark);

      String selectGuestNum = "SELECT numGuest FROM booking WHERE id = ?";
      int guestNum = this.jdbcTemplate.queryForObject(selectGuestNum, Integer.class, id);
      booking.setNumGuest(guestNum);

      String selectUserId = "SELECT user_id FROM booking WHERE id = ?";
      int userId = this.jdbcTemplate.queryForObject(selectUserId, Integer.class, id);
      booking.setUserId(userId);

      String selectPropertyId = "SELECT property_id FROM booking WHERE id = ?";
      int propertyId = this.jdbcTemplate.queryForObject(selectPropertyId, Integer.class, id);
      booking.setUserId(propertyId);
    }
}