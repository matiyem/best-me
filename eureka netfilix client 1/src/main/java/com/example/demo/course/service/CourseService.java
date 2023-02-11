package com.example.demo.course.service;


import com.example.demo.course.model.Course;
import com.example.demo.course.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> allCourses();

    Course findCourseById(Long courseId);

    List<Transaction> findTransactionsOfUser(Long userId);

    List<Transaction> findTransactionsOfCourse(Long courseId);

    Transaction saveTransaction(Transaction transaction);

    void deleteTransaction(Long id);

    Optional<Transaction> findTransactionByUserAndCourseId(Long userId, Long courseId);


}
