package huffman;

import huffman.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Иван on 09.12.2014.
 */
public class ArchivatorHuffman {

    // Архивируем файл с путем inPath в директорию outPath
    // имя архива: fileName_расширение
    public void archiving(String inPath, String outPath) throws IOException {

        MyHuffman coder = new MyHuffman();
        String fileName = inPath.substring(inPath.lastIndexOf(File.separator)+1);   // преобразуем имя файла : имя.расширение
        String archiveName = fileName.replace('.', '_');                            // в имя_расширеие
        MyFile inFile = new MyFile();
        inFile.readFrom(inPath);
        MyFile archivedFile = new MyFile(coder.encode(inFile));
        archivedFile.writeTo(outPath + archiveName);
    }

    //Разархивируем файл с путем inPath в директоию outPath
    public void deArchiving(String inPath, String outPath) throws IOException {

        MyHuffman coder = new MyHuffman();
        String archiveName = inPath.substring(inPath.lastIndexOf(File.separator)+1);
        String fileName = archiveName.replace('_','.');
        MyFile inFile = new MyFile();
        inFile.readFrom(inPath);
        MyFile decodeFile = new MyFile(coder.decode(inFile));
        decodeFile.writeTo(outPath+fileName);
    }

}
