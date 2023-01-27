package com.example.demo.Feign.controller;

import com.example.demo.Feign.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
    Create by Atiye Mousavi 
    Date: 1/11/2023
    Time: 10:30 AM
**/
@FeignClient(name="CLIENT-SERVICE-1")
public interface BookRestConsumer {

    @GetMapping("/clientService1/data")
    public String getBookData();

    @GetMapping("/clientService1/{id}")
    public Book getBookById(@PathVariable Integer id);

    @GetMapping("/clientService1/all")
    public List<Book> getAllBooks();

    @GetMapping("/clientService1/entity")
    public ResponseEntity<String> getEntityData();
}