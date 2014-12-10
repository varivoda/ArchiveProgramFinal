package interfaces;

import java.io.File;
import java.io.IOException;

/**
 * Created by root on 10.12.14.
 */
public interface Archivator {

    public void archiving(String inString, String outPath) throws IOException;
    public void deArchiving(String inPath, String outPath) throws IOException;

}
