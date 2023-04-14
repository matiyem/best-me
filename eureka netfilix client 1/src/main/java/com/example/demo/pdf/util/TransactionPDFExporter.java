package com.example.demo.pdf.util;

import com.example.demo.course.model.Transaction;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/*
    Create by Atiye Mousavi 
    Date: 2/20/2023
    Time: 2:50 PM
**/
public class TransactionPDFExporter {
    private List<Transaction> listTransaction;

    public TransactionPDFExporter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("title", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("author", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("category", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("dateOfIssue", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction list : listTransaction) {
            table.addCell(String.valueOf(list.getId()));
            table.addCell(list.getCourse().getTitle());
            table.addCell(list.getCourse().getAuthor());
            table.addCell(list.getCourse().getCategory());
            table.addCell(String.valueOf(list.getDateOfIssue()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}