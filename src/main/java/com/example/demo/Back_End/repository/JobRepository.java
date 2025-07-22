package com.example.demo.Back_End.repository;

import com.example.demo.Back_End.entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Job SET status='Deactivate' WHERE id=?1", nativeQuery = true)
    void changeJobStatus(String id);
}
