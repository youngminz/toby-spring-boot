package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            int sum = 0;
            String line;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
            return sum;
        }
    }
}