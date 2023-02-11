package com.example.demo.course.controller;

import com.example.demo.course.intercomm.UserClient;
import com.example.demo.course.model.Transaction;
import com.example.demo.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientService1")
public class CourseController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String serviceId;

    @GetMapping("/service/port")
    public String getPort() {
        return "Service is working at port : " + env.getProperty("local.server.port");
    }

    @GetMapping("/service/instances")
    public ResponseEntity<?> getInstances() {
        return ResponseEntity.ok(discoveryClient.getInstances(serviceId));
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<?> findTransactionsOfUser(@PathVariable Long userId) {
        return ResponseEntity.ok(courseService.findTransactionsOfUser(userId));
    }

    @GetMapping("/service/all")
    public ResponseEntity<?> findAllCourses() {
        return ResponseEntity.ok(courseService.allCourses());
    }

    @PostMapping("/service/enroll")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        transaction.setDateOfIssue(LocalDateTime.now());
        Optional<Transaction> transactionsOfCourse = courseService.findTransactionByUserAndCourseId(transaction.getUserId(),transaction.getCourse().getId());
        if (!transactionsOfCourse.isEmpty()) {
            transaction.setStatusId(1);
            transaction.setStatusMessage("This lesson is repetitive");
            return new ResponseEntity<>(transaction, HttpStatus.OK);

        } else {
            transaction.setStatusId(2);

            transaction.setCourse(courseService.findCourseById(transaction.getCourse().getId()));
            transaction = courseService.saveTransaction(transaction);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }
    }

    @GetMapping("/service/course/{courseId}")
    public ResponseEntity<?> findStudentsOfCourse(@PathVariable Long courseId) {
        List<Transaction> transactions = courseService.findTransactionsOfCourse(courseId);
        if (CollectionUtils.isEmpty(transactions)) {
            return ResponseEntity.notFound().build();
        }
        List<Long> userIdList = transactions.parallelStream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> students = userClient.getUserNames(userIdList);
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/service/deleteCourse/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        courseService.deleteTransaction(transactionId);
        return new ResponseEntity<>("The lesson was successfully deleted", HttpStatus.CREATED);


    }
}
