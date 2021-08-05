package by.karpov.webcrawler.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class WriterUtil {
    /**
     * Saves the result of the program to a file
     * @param fileName file name to save
     * @param stringsForCSV file contents to save
     * @throws IOException  if an I/O error occurs or the parent directory does not exist
     * and if writing to or creating the file,
     * or the text cannot be encoded using the specified charset
     */
    public static void writeCSVFile(String fileName, List<String> stringsForCSV) throws IOException{
        Path path = Files.createFile(Paths.get(fileName));
        Files.write(path, stringsForCSV, StandardCharsets.UTF_8);
    }
}
