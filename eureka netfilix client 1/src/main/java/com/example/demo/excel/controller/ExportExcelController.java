package com.example.demo.excel.controller;
/*
    created by Atiye Mousavi
    Date: 1/6/2023
    Time: 3:57 PM
*/


import com.example.demo.course.model.Transaction;
import com.example.demo.course.service.CourseService;
import com.example.demo.excel.util.TransactionExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/clientService1")
public class ExportExcelController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/service/profileExport/excel/{userId}")
    public void exportToExcel(HttpServletResponse response, @PathVariable Long userId) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=prof_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listTransaction = courseService.findTransactionsOfUser(userId);

        TransactionExcelExporter excelExporter = new TransactionExcelExporter(listTransaction);

        excelExporter.export(response);
    }
}