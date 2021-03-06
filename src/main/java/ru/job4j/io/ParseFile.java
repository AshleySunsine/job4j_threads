package ru.job4j.io;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.Objects;

@ThreadSafe
public class ParseFile {
    @GuardedBy("this")
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        final GetFileContent getContent = new GetContent(file);
        return getContent.content(Objects::nonNull);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        final GetFileContent getContent = new GetContent(file);
        return getContent.content(i -> i < 0x08);
    }
}