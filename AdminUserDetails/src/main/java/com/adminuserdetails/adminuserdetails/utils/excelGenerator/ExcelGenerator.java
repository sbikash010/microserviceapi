package com.adminuserdetails.adminuserdetails.utils.excelGenerator;

import com.adminuserdetails.adminuserdetails.pojo.adminPojo.AdminPojo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelGenerator {
    private List<AdminPojo> listRecords;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<AdminPojo> listRecords) {
        this.listRecords = listRecords;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Admin Records");

        Row row = sheet.createRow(0);



        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "id", style);
        createCell(row, 1, "name", style);
        createCell(row, 2, "address", style);
        createCell(row, 3, "contactNumber", style);
        createCell(row, 4, "date", style);
        createCell(row, 5, "email", style);
        createCell(row, 6, "filepath", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (AdminPojo record : listRecords) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

                createCell(row, columnCount++, record.getId(), style);
                createCell(row, columnCount++, record.getName(), style);
                createCell(row, columnCount++, record.getAddress(), style);
                createCell(row, columnCount++, record.getContactNumber(), style);
                createCell(row, columnCount++, record.getDate(), style);
                createCell(row, columnCount++, record.getEmail(), style);
                createCell(row, columnCount++, record.getFileLocation(), style);


        }
    }

    public void generate(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
