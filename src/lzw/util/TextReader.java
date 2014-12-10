package lzw.util;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex
 */
public interface TextReader {

    public List<String> readTextFile(String fileName) throws IOException;

}

