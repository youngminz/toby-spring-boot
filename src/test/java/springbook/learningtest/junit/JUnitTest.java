package springbook.learningtest.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JUnitTest {
    static JUnitTest testObject;

    @Test
    public void test1() {
        assertNotEquals(this, testObject);
        testObject = this;
    }

    @Test
    public void test2() {
        assertNotEquals(this, testObject);
        testObject = this;
    }

    @Test
    public void test3() {
        assertNotEquals(this, testObject);
        testObject = this;
    }
}
