package springbook.learningtest.proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProxyTest {
    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertEquals(hello.sayHello("Toby"), "Hello Toby");
        assertEquals(hello.sayHi("Toby"), "Hi Toby");
        assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");
    }
}
