package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class GetContentWithoutUnicode implements GetFileContent {
    private final File file;

    public GetContentWithoutUnicode(File file) {
        this.file = file;
    }

    @Override
    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                char c = Integer.toString(data).charAt(0);
                if (filter.test(c)) {
                    output.append(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
