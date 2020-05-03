package com.qualitydefectdetector.reader;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TxtReader {

    public static List<String> readFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String completeData = new String(bytes);
            String[] userStories = completeData.split("\n");
            return Arrays.stream(userStories)
                    .filter(userStory -> !userStory.equals(""))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Boş Dosya");
        }
    }
}
