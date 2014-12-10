package lzw;

import lzw.util.TextReaderImpl;
import lzw.util.TextWriterImpl;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Иван on 09.12.2014.
 */
public class ArchivatorLZW {

    // Архивируем файл с путем inPath в директорию outPath
    // имя архива: fileName_расширение
    public void archiving(String inPath, String outPath) throws IOException {

        TextReaderImpl textReader = new TextReaderImpl();
        ArchiveByLZWImpl coder = new ArchiveByLZWImpl();
        List<String> stringFile = textReader.readTextFile(inPath);
        List<String> archivedStringFile = coder.archive(stringFile);
        String fileName = inPath.substring(inPath.lastIndexOf(File.separator)+1);
        String archiveName = fileName.replace('.', '_');
        TextWriterImpl textWriter = new TextWriterImpl(outPath + archiveName);
        textWriter.writeStringListInFile(archivedStringFile);
    }

    //Разархивируем файл с путем inPath в директоию outPath
    public void deArchiving(String inPath, String outPath) throws IOException {

        ArchiveByLZWImpl coder = new ArchiveByLZWImpl();
        TextReaderImpl textReader = new TextReaderImpl();
        List<String> archivedFile = textReader.readTextFile(inPath);
        List<String> stringFile = coder.dearchive(archivedFile);
        String archiveName = inPath.substring(inPath.lastIndexOf(File.separator)+1);
        String fileName = archiveName.replace('_','.');
        TextWriterImpl textWriter = new TextWriterImpl(outPath+fileName);
        textWriter.writeStringListInFile(stringFile);
    }



}
