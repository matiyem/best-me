package com.example.demo.feign.controller;

import com.example.demo.feign.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    Create by Atiye Mousavi 
    Date: 1/11/2023
    Time: 9:45 AM
**/
@RestController
@RequestMapping("/clientService1")
public class BookRestController {

    @Autowired
    Environment environment;

    @GetMapping("/data")
    public String getBookData() {

        return "data of CART-SERVICE, Running on port: " + environment.getProperty("local.server.port");
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return new Book(id, "Head First Java", 500.75);
    }

    @GetMapping("/all")
    public List<Book> getAll() {
        return List.of(
                new Book(501, "Head First Java", 439.75),
                new Book(502, "Spring in Action", 340.75),
                new Book(503, "Hibernate in Action", 355.75)
        );
    }

    @GetMapping("/entity")
    public ResponseEntity<String> getEntityData() {
        return new ResponseEntity<String>(
                "Hello from BookRestController",
                HttpStatus.OK);
    }
}