package huffman;

import java.io.*;

/**
 * Created by GODLIKE on 03.12.2014.
 */
public class MyFile {
    File file;
    byte[] bytes;
    String path;

    public MyFile(){
    }

    public MyFile(byte[] Bytes){
        path="";
        bytes = Bytes;
        file = null;
    }

    public void readFrom(String Path) throws IOException {
        path = Path;
        file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            throw new IOException("File " + file.getName() + " is too large");
        }

        bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;

        while (offset < bytes.length
                && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {

            offset += numRead;
        }

        if (offset < bytes.length) {

            throw new IOException("Could not completely read file " + file.getName());

        }

        inputStream.close();
    }

    public File writeTo(String path) throws FileNotFoundException, IOException{
        File file = new File(path);

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(bytes);

        fileOutputStream.close();

        return file;
    }
}
