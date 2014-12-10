package huffman;

import interfaces.Archivator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Иван on 09.12.2014.
 */
public class ArchivatorHuffman implements Archivator{

    static final int SIZE_ARRAY = 1048576;

    // Архивируем файл с путем inPath в директорию outPath
    // имя архива: fileName_расширение
    public void archiving(String inString, String outPath) throws IOException {

        File inFile = new File(inString);
        MyHuffman coder = new MyHuffman();
        FileInputStream fileInputStream = new FileInputStream(inFile);
        String fileName = inFile.getPath().substring(inFile.getPath().lastIndexOf(File.separator) + 1);   // преобразуем имя файла : имя.расширение
        String archiveName = fileName.replace('.', '_');                            // в имя_расширеие
        int countFullArrays = (int) (inFile.length() / (long) SIZE_ARRAY);
        List<byte[]> archivedByteList = new ArrayList<>(countFullArrays+1);

        for (int i = 0; i < countFullArrays; i++){
            byte[] currentPart = new byte[SIZE_ARRAY];
            fileInputStream.read(currentPart);
            MyFile curMyFile = new MyFile(currentPart);
            archivedByteList.add(coder.encode(curMyFile));
//            System.out.println(i + " " + archivedByteList.get(archivedByteList.size()-1).length + " " + archivedByteList.get(archivedByteList.size()-1)[0] );
        }
        if ( inFile.length() - (long) countFullArrays* (long) SIZE_ARRAY > 0){
            byte[] lastBytes = new byte[(int) (inFile.length() - (long) countFullArrays* (long) SIZE_ARRAY)];
            fileInputStream.read(lastBytes);
            MyFile curMyFile = new MyFile(lastBytes);
            archivedByteList.add(coder.encode(curMyFile));
//            System.out.println("last" + ' ' + archivedByteList.get(archivedByteList.size()-1).length + ' ' + archivedByteList.get(archivedByteList.size()-1)[0] );
        }

        if (fileInputStream.read() > 0){
            throw  new IOException("Error in reading");
        }
        fileInputStream.close();
        File outFile = new File(outPath + archiveName);
        boolean flag = outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        for (byte[] curArray : archivedByteList ){
            String partSize = Integer.toString(curArray.length);
            byte strSize = (byte) partSize.getBytes().length;
            fileOutputStream.write(strSize);
            fileOutputStream.write(partSize.getBytes());
            fileOutputStream.write(curArray);
        }
        fileOutputStream.close();
    }

    //Разархивируем файл с путем inPath в директоию outPath
    public void deArchiving(String inString, String outPath) throws IOException {

        File inFile = new File(inString);
        MyHuffman coder = new MyHuffman();
        String archiveName = inFile.getName();
        String fileName = archiveName.replace('_','.');
        FileInputStream fileInputStream = new FileInputStream(inFile);
        List<byte[]> byteFile = new ArrayList<>();
        int i =0;
        int sizeCurStringSize =  fileInputStream.read();
        while (sizeCurStringSize > 0){
            // считываем байтовое представление строки - размера
            byte[] byteString = new byte[sizeCurStringSize];
            fileInputStream.read(byteString);
            String stringSize = new String(byteString);
            int sizeArchivedPart = Integer.parseInt(stringSize);

            byte[] archivedPart = new byte[sizeArchivedPart];
            fileInputStream.read(archivedPart);
//            System.out.println(i++ + " " + archivedPart.length + " " + archivedPart[0] );
            MyFile curMyFile = new MyFile(archivedPart);
            byteFile.add(coder.decode(curMyFile));
            sizeCurStringSize = fileInputStream.read();
        }
        fileInputStream.close();

        File outFile = new File(outPath+fileName);
        outFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        for (byte[] curArray : byteFile){
            fileOutputStream.write(curArray);
        }
        fileOutputStream.close();
    }

}
