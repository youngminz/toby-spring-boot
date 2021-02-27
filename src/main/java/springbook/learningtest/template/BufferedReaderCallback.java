package springbook.learningtest.template;

import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomethingWithReader(String line, Integer value) throws IOException;
}
