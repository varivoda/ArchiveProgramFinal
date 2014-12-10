package huffman;

import java.io.File;

/**
 * Created by GODLIKE on 03.12.2014.
 */
public interface ICoder {

    byte[] encode(MyFile file);

    byte[] decode(MyFile file);

}
