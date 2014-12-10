package lzw.util;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex
 */

public interface TextWriter {

    /**
     * Writes list of strings in the file
     *
     * @throws IOException if file not found
     */

    public void writeStringListInFile(List<String> outList) throws IOException;

}
