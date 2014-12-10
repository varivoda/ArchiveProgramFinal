package lzw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReaderImpl implements TextReader {

    @Override
    public List<String> readTextFile(String fileName) throws IOException {
        List<String> stringArray = new ArrayList<>();
        File textFile = new File(fileName);
        if (!textFile.isFile()) {
            throw new IOException("File not found");
        }
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        String line;
        while ((line = reader.readLine()) != null) {
            stringArray.add(line);
        }
        reader.close();
        return stringArray;
    }
}

