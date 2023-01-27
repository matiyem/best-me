package com.example.demo.feign.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Create by Atiye Mousavi 
    Date: 1/11/2023
    Time: 9:39 AM
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer bookId;
    private String bookName;
    private Double bookCost;
}
