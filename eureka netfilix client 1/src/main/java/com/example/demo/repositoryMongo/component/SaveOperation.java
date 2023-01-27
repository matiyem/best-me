package com.example.demo.repositoryMongo.component;

/*
    created by Atiye Mousavi
    Date: 1/14/2023
    Time: 1:44 PM
*/


import com.example.demo.repositoryMongo.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SaveOperation  {

    @Autowired
    private MongoTemplate mongoRepo;

    public void saveInMongo(Log log){
        mongoRepo.save(log);

    }


}