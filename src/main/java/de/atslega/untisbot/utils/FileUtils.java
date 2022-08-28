package de.atslega.untisbot.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static String readAll(Path path) throws IOException {
        return new String(Files.readAllBytes(path), UTF8);
    }

    public static void writeAll(File file, String context) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        file.createNewFile();
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            out.write(context);
        }
    }

    public static String read(Path path) throws IOException {
        return Files.readString(path, UTF8);
    }

    public static void write(Path path, String contents) throws IOException {
        Files.writeString(path, contents, UTF8);
    }
}
