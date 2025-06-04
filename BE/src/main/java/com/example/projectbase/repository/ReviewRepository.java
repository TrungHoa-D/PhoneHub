package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("SELECT r FROM Review r WHERE r.id = ?1")
    Optional<Review> findById(String id);

    List<Review> findAllByUser_Id(String userId);

    List<Review> findAllByPhone_Id(String phoneId);

    Review findByPhone_IdAndUser_Id(String phoneId, String userId);
}
