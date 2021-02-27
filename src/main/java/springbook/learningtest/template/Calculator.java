package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        BufferedReaderCallback sumCallback = (line, value) -> value + Integer.parseInt(line);
        return fileReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        BufferedReaderCallback multiplyCallback = (line, value) -> value * Integer.parseInt(line);
        return fileReadTemplate(filepath, multiplyCallback, 1);
    }

    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback, int initVal) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            int res = initVal;
            String line;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithReader(line, res);
            }
            return res;
        }
    }
}
