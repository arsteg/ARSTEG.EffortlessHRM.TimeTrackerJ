package com.timetracker.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.List;

public class Export<T> {
    public void invokeExport(List<T> listToExport, List<String[]> columnHeaders, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.isDirectory() || f.getName().endsWith(".xlsx");
            }

            @Override
            public String getDescription() {
                return "Excel Files (*.xlsx)";
            }
        });

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            if (!fileName.endsWith(".xlsx")) {
                fileName += ".xlsx";
            }
            exportToExcel(listToExport, columnHeaders, title, fileName);
        }
    }

    private void exportToExcel(List<T> listToExport, List<String[]> columnHeaders, String reportTitle, String fileName) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(reportTitle);

            // Headers
            Row headerRow = sheet.createRow(0);
            int col = 0;
            for (String[] header : columnHeaders) {
                for (String h : header) {
                    Cell cell = headerRow.createCell(col++);
                    cell.setCellValue(h);
                    CellStyle style = wb.createCellStyle();
                    Font font = wb.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    cell.setCellStyle(style);
                }
            }

            // Data
            if (listToExport != null && !listToExport.isEmpty()) {
                int rowNum = 1;
                for (T item : listToExport) {
                    Row row = sheet.createRow(rowNum++);
                    // Add logic to extract fields from T (use reflection or specific getters)
                    // Example: row.createCell(0).setCellValue(item.toString());
                }
            }

            // Auto-size columns
            for (int i = 0; i < col; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                wb.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}