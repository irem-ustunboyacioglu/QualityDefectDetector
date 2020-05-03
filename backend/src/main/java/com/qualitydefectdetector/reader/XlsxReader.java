package com.qualitydefectdetector.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class XlsxReader {

    private XlsxReader() {
    }

    public static List<String> readFile(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet workSheet = workbook.getSheetAt(0);
            Row row = workSheet.getRow(workSheet.getFirstRowNum());

            int indexOfUserStories = 0;
            for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
                String lowerCasePart = row.getCell(i).getStringCellValue().toLowerCase();
                if (lowerCasePart.equals("kullanıcı hikayesi") || lowerCasePart.equals("user story") ||
                        lowerCasePart.equals("kullanıcı hikayeleri") || lowerCasePart.equals("user stories")) {
                    indexOfUserStories = i;
                    break;
                }
            }
            int columnOfUserStories = indexOfUserStories;

            List<String> userStories = new ArrayList<>();
            for (int i = workSheet.getFirstRowNum() + 1; i <= workSheet.getLastRowNum(); i++) {
                Row currentRow = workSheet.getRow(i);
                userStories.add(currentRow.getCell(columnOfUserStories).getStringCellValue());
            }

            return userStories.stream()
                    .filter(userStory -> !userStory.equals(""))
                    .collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
