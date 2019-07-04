package com.example.testjdbc.repositories;

import java.util.List;

import com.example.testjdbc.entities.Review;
import com.example.testjdbc.mappers.ReviewRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Review> getAllReviews() {                                                                                                                                   
        String sql = "SELECT * FROM \"review\"";
        RowMapper<Review> rowMapper = new ReviewRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public List<Review> getReviewsByPropertyId(int id){
        String sql = "SELECT * FROM review WHERE property_id = ?";
        RowMapper<Review> rowMapper = new ReviewRowMapper();
        return this.jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
    }

    public void createReview(Review review, int propertyId) {
        String sql = "INSERT INTO \"review\" VALUES (?, ?, ?)";
        int rating = review.getRating();
        String remark = review.getRemark();
        this.jdbcTemplate.update(sql, rating, remark, propertyId);

        review.setPropertyId(propertyId);

        String selectReviewId = "SELECT TOP 1 id FROM review ORDER BY id DESC";
        int reviewId = this.jdbcTemplate.queryForObject(selectReviewId, Integer.class);
        review.setId(reviewId);
        
    }
}