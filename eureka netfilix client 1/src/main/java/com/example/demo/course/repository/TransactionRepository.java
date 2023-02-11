package com.example.demo.course.repository;

import com.example.demo.course.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findAllByCourseId(Long courseId);

    void deleteById(Long id);

//    @Query("select * from Transaction u where u.userId=:userId and u.course.id=:courseId")
    Optional<Transaction> findByUserIdAndCourseId( Long userId,  Long courseId);
}
