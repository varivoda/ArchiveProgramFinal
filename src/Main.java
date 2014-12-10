import graph_interface.ArchiveFrame;
import huffman.ArchivatorHuffman;
import lzw.ArchivatorLZW;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Иван on 09.12.2014.
 */
public class Main {

    // Можно протестировать без интерфейса



//
//    public static void main(String[] args) {
//
//        // It works, fuck yeah!
//
////        ArchivatorHuffman archivator = new ArchivatorHuffman();
////        try {
////            archivator.archiving("C:\\Example\\autobiography.doc","C:\\ResultExample\\" );
////            System.out.println("Decoding");
////            archivator.deArchiving("C:\\ResultExample\\autobiography_doc", "C:\\ResultExample\\" );
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        ArchivatorLZW archivator = new ArchivatorLZW();
//        try {
//            archivator.archiving("C:\\Example\\Lexa.doc", "C:\\ResultExample\\");
//            System.out.println("Decoding");
////            archivator.deArchiving("C:\\ResultExample\\Lexa_doc", "C:\\ResultExample\\");  // Леха, здесь валится !!!!!!
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new ArchiveFrame();
            }
        });
    }
}
