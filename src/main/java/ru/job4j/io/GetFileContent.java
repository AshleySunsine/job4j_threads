package ru.job4j.io;

import java.io.FileNotFoundException;
import java.util.function.Predicate;

public interface GetFileContent {
    String content(Predicate<Character> filter) throws FileNotFoundException;
}
