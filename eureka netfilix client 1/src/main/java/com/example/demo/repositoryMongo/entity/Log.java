package com.example.demo.repositoryMongo.entity;

/*
    created by Atiye Mousavi
    Date: 1/14/2023
    Time: 12:59 PM
*/


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Document
public class Log {

    @Id
    private String id;

    @NonNull
    private String log;

}