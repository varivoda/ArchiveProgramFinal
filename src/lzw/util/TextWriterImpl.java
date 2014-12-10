package lzw.util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TextWriterImpl implements TextWriter{

    private String fileDirectoryAndNameOfFile;

    public TextWriterImpl(String fileDirectory){
        this.fileDirectoryAndNameOfFile = fileDirectory;
    }

    @Override
    public void writeStringListInFile(List<String> outList) throws IOException {
        File outputFile = new File(fileDirectoryAndNameOfFile);
        outputFile.createNewFile();
        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));
        for (String str : outList){
            writer.write(str + "\n");
        }
        writer.close();
    }
}
