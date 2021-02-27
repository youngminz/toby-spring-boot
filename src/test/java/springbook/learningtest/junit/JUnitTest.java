package springbook.learningtest.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class JUnitTest {
    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test1() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }

    @Test
    public void test2() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }

    @Test
    public void test3() {
        assertFalse(testObjects.contains(this));
        testObjects.add(this);
    }
}
