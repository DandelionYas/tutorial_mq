package root.service.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static void write(File file, String text) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            bufferedWriter.write(text);
        }
    }
}