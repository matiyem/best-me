package com.example.demo.pdf.controller;

import com.example.demo.course.model.Transaction;
import com.example.demo.course.service.CourseService;
import com.example.demo.pdf.util.TransactionPDFExporter;
import com.lowagie.text.DocumentException;
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

/*
    Create by Atiye Mousavi 
    Date: 2/20/2023
    Time: 2:55 PM
**/
@Controller
@RequestMapping("/clientService1")
public class ExportPdfController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/service/exportPdf/{userId}")
    public void exportToPDF(HttpServletResponse response,@PathVariable Long userId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listTransaction = courseService.findTransactionsOfUser(userId);

        TransactionPDFExporter exporter = new TransactionPDFExporter(listTransaction);
        exporter.export(response);

    }
}