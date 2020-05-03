package com.qualitydefectdetector.reader;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

    private CsvReader() {
    }

    public static List<String> readFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String completeData = new String(bytes);
            String[] rows = completeData.split("\n");
            if (rows.length == 1) {
                throw new IllegalArgumentException("Boş Dosya");
            }

            int indexOfUserStories = 0;
            String[] firstRowParts = rows[0].split(",");
            for (int i = 0; i < firstRowParts.length; i++) {
                String lowerCasePart = firstRowParts[i].toLowerCase();
                if (lowerCasePart.equals("kullanıcı hikayesi") || lowerCasePart.equals("user story") ||
                        lowerCasePart.equals("kullanıcı hikayeleri") || lowerCasePart.equals("user stories")) {
                    indexOfUserStories = i;
                    break;
                }
            }
            int columnOfUserStories = indexOfUserStories;

            List<String> rowList = Arrays.stream(rows)
                    .filter(s -> !s.isEmpty()) // filter empty rows
                    .filter(s -> !s.split(",")[columnOfUserStories].equals("")) //get the user story columns
                    .collect(Collectors.toList());

            return rowList
                    .subList(1, rowList.size())
                    .stream()
                    .map(s -> s.split(",")[columnOfUserStories]) //get the user story columns
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Boş Dosya");
        }
    }
}
