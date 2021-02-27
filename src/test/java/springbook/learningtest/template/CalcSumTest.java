package springbook.learningtest.template;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CalcSumTest {
    Calculator calculator;
    String numFilepath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilepath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertEquals(calculator.calcSum(this.numFilepath), 10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertEquals(calculator.calcMultiply(this.numFilepath), 24);
    }
}
