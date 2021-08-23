package ru.job4j.io;

import java.io.*;
import java.util.Objects;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        final GetFileContent getContent = new GetContent(file);
        return getContent.content(Objects::nonNull);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        final GetFileContent getContent = new GetContentWithoutUnicode(file);
        return getContent.content(i -> i < 0x08);
    }
}